package ru.skillbranch.gameofthrones.repositories

import android.annotation.SuppressLint
import android.net.Uri
import androidx.annotation.VisibleForTesting
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import ru.skillbranch.gameofthrones.App
import ru.skillbranch.gameofthrones.data.local.CharacterDao
import ru.skillbranch.gameofthrones.data.local.GameOfThronesDatabase
import ru.skillbranch.gameofthrones.data.local.HouseDao
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.local.entities.RelativeCharacter
import ru.skillbranch.gameofthrones.data.remote.HouseApi
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import ru.skillbranch.gameofthrones.data.remote.res.shortName
import ru.skillbranch.gameofthrones.data.toCharacter
import ru.skillbranch.gameofthrones.data.toHouse
import javax.inject.Inject

object RootRepository {
    @set:Inject
    var characterDao: CharacterDao? = null

    @set:Inject
    var houseDao: HouseDao? = null

    @set:Inject
    var housesApi: HouseApi? = null

    @set:Inject
    var database: GameOfThronesDatabase? = null

    init {
        App.component.inject(this)
    }

    /**
     * Получение данных о всех домах
     * @param result - колбек содержащий в себе список данных о домах
     */
    @SuppressLint("CheckResult")
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getAllHouses(result: (houses: List<HouseRes>) -> Unit) {
        val fullList = mutableListOf<HouseRes>()
        getPageAndNext(1)
            .concatMap { response ->
                return@concatMap Observable.just(response.body() as List<HouseRes>)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> fullList.addAll(response) },
                { error -> error.printStackTrace() },
                { result(fullList) })
    }

    private fun getPageAndNext(page: Int): Observable<Response<List<HouseRes>>> =
        housesApi?.getHousesByPage(page)!!
            .concatMap { response ->
                val headerLinkStr = response.headers().get("link")
                if (!headerLinkStr!!.contains(("rel=\"next\"")))
                    return@concatMap Observable.just(response)
                val nextLinkStr = headerLinkStr.substringBefore("rel=\"next\"")
                val headerLinkUrl = Uri.parse(nextLinkStr)
                val nextUrl = headerLinkUrl.getQueryParameter("page")
                return@concatMap Observable.just(response)
                    .concatWith(getPageAndNext(nextUrl!!.toInt()))
            }


    /**
     * Получение данных о требуемых домах по их полным именам (например фильтрация всех домов)
     * @param houseNames - массив полных названий домов (смотри AppConfig)
     * @param result - колбек содержащий в себе список данных о домах
     */
    @SuppressLint("CheckResult")
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getNeedHouses(vararg houseNames: String, result: (houses: List<HouseRes>) -> Unit) {
        val needHouses = mutableListOf<HouseRes>()

        getNeedHousesObservable(houseNames.toList())
            .subscribe(
                { housesRes -> if (housesRes.isNullOrEmpty().not()) needHouses.add(housesRes[0]) },
                { error -> error.printStackTrace() },
                { result(needHouses) })
    }

    private fun getNeedHousesObservable(houseNames: List<String>): Observable<List<HouseRes>> =
        Observable.fromArray(houseNames)
            .flatMapIterable { houses -> houses }
            .flatMap { house -> housesApi?.getHousesByName(house) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    /**
     * Получение данных о требуемых домах по их полным именам и персонажах в каждом из домов
     * @param houseNames - массив полных названий домов (смотри AppConfig)
     * @param result - колбек содержащий в себе список данных о доме и персонажей в нем (Дом - Список Персонажей в нем)
     */
    @SuppressLint("CheckResult")
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getNeedHouseWithCharacters(
        vararg houseNames: String,
        result: (houses: List<Pair<HouseRes, List<CharacterRes>>>) -> Unit
    ) {
        val needHousesWithCharacters = mutableListOf<Pair<HouseRes, List<CharacterRes>>>()

        getNeedHousesObservable(houseNames.toList())
            .filter { housesRes -> housesRes.isNullOrEmpty().not() }
            .flatMap { houseRes -> getCharactersByHouse(houseRes[0]) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { houseWithCharacters -> needHousesWithCharacters.add(houseWithCharacters) },
                { error -> error.printStackTrace() },
                { result(needHousesWithCharacters) })
    }

    private fun getCharactersByHouse(houseRes: HouseRes): Observable<Pair<HouseRes, MutableList<CharacterRes>>>? =
        Observable.fromArray(houseRes.swornMembers)
            .flatMapIterable { swornMembersList -> swornMembersList }
            .flatMap { swornMember ->
                val idCharacter = swornMember.split("/").last()
                housesApi?.getCharacterById(idCharacter)
            }
            .map { characterRes -> characterRes.apply { houseId = houseRes.shortName() } }
            .toList()
            .toObservable()
            .flatMap { characterRes -> Observable.just(houseRes to characterRes) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    /**
     * Запись данных о домах в DB
     * @param houses - Список персонажей (модель HouseRes - модель ответа из сети)
     * необходимо произвести трансформацию данных
     * @param complete - колбек о завершении вставки записей db
     */
    @SuppressLint("CheckResult")
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun insertHouses(houses: List<HouseRes>, complete: () -> Unit) {
        Completable.fromAction {
            houseDao?.insertHouses(houses.map { it.toHouse() })
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { complete() },
                { error -> error.printStackTrace() })

    }

    /**
     * Запись данных о пересонажах в DB
     * @param characters - Список персонажей (модель CharacterRes - модель ответа из сети)
     * необходимо произвести трансформацию данных
     * @param complete - колбек о завершении вставки записей db
     */
    @SuppressLint("CheckResult")
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun insertCharacters(characters: List<CharacterRes>, complete: () -> Unit = {}) {
        Completable.fromAction {
            characterDao?.insertCharacters(characters.map { it.toCharacter() })
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { complete() },
                { error -> error.printStackTrace() })
    }

    /**
     * При вызове данного метода необходимо выполнить удаление всех записей в db
     * @param complete - колбек о завершении очистки db
     */
    @SuppressLint("CheckResult")
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun dropDb(complete: () -> Unit) {
        Completable.fromAction {
            database?.clearAllTables()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { complete() },
                { error -> error.printStackTrace() })
    }

    /**
     * Поиск всех персонажей по имени дома, должен вернуть список краткой информации о персонажах
     * дома - смотри модель CharacterItem
     * @param name - краткое имя дома (его первычный ключ)
     * @param result - колбек содержащий в себе список краткой информации о персонажах дома
     */
    @SuppressLint("CheckResult")
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun findCharactersByHouseName(name: String, result: (Characters: List<CharacterItem>) -> Unit) {
        characterDao!!.findCharactersByHouseName(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { characters -> result(characters) },
                { error -> error.printStackTrace() })
    }

    /**
     * Поиск персонажа по его идентификатору, должен вернуть полную информацию о персонаже
     * и его родственных отношения - смотри модель CharacterFull
     * @param id - идентификатор персонажа
     * @param result - колбек содержащий в себе полную информацию о персонаже
     */
    @SuppressLint("CheckResult")
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun findCharacterFullById(id: String, result: (Character: CharacterFull) -> Unit) {
        characterDao!!.findCharacterFullById(id)
            .flatMap { characterFull ->
                Maybe.just(characterFull)
                    .flatMap {
                        characterFull.father ?: Maybe.just(characterFull)
                        if (characterFull.father!!.id.isNotEmpty())
                            characterDao?.findRelativeCharacterById(characterFull.father.id)!!
                                .doOnComplete { Maybe.just(characterFull.copy(father = null)) }
                                .flatMap { fatherRelative ->
                                    Maybe.just(characterFull.copy(father = fatherRelative))
                                }
                        else
                            Maybe.just(characterFull)
                    }

            }
            .flatMap { characterFull ->
                Maybe.just(characterFull)
                    .flatMap {
                        characterFull.mother ?: Maybe.just(characterFull)
                        if (characterFull.mother!!.id.isNotEmpty())
                            characterDao?.findRelativeCharacterById(characterFull.mother.id)!!
                                .onErrorReturnItem(characterFull.mother.copy(id = "", name = "", house = ""))
                                .flatMap { motherRelative ->
                                    Maybe.just(characterFull.copy(mother = motherRelative))
                                }
                                .doOnComplete { Maybe.just(characterFull.copy(mother = null)) }
                        else Maybe.just(characterFull)
                    }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { characters -> result(characters) },
                { error -> error.printStackTrace() },
                { throw IllegalArgumentException("No such character") })
    }

    /**
     * Метод возвращет true если в базе нет ни одной записи, иначе false
     * @param result - колбек о завершении очистки db
     */
    @SuppressLint("CheckResult")
    fun isNeedUpdate(result: (isNeed: Boolean) -> Unit) {
        Single.zip(houseDao?.getCountEntity(),
            characterDao?.getCountEntity(),
            BiFunction { countHouses: Int, countCharacters: Int -> countHouses + countCharacters })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { countSum -> result(countSum == 0) },
                { error -> error.printStackTrace() })
    }
}
