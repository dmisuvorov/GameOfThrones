package ru.skillbranch.gameofthrones.repositories

import android.annotation.SuppressLint
import android.net.Uri
import androidx.annotation.VisibleForTesting
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.skillbranch.gameofthrones.data.local.CharacterDao
import ru.skillbranch.gameofthrones.data.local.HouseDao
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.remote.HouseApi
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import ru.skillbranch.gameofthrones.di.DaggerRepositoryComponent
import javax.inject.Inject

class RootRepositoryHelper @Inject constructor() {
    @field:Inject
    lateinit var characterDao: CharacterDao

    @set:Inject
    lateinit var houseDao: HouseDao

    @Inject
    lateinit var housesApi: HouseApi
}

object RootRepository {

    @set:Inject
    lateinit var houseApi: HouseApi

    init {
        DaggerRepositoryComponent.builder().build().inject(this)
    }
    /**
     * Получение данных о всех домах
     * @param result - колбек содержащий в себе список данных о домах
     */
    @SuppressLint("CheckResult")
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getAllHouses(result: (houses: List<HouseRes>) -> Unit) {
        getPageAndNext(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ houses -> result(houses) },
                { error -> error.printStackTrace() })
    }

    private fun getPageAndNext(page: Int): Observable<List<HouseRes>> =
        houseApi.getHousesByPage(page)
            .concatMap { response ->
                val nextUrlStr = response.headers().get("link")
                nextUrlStr ?: Observable.just(response.body())
                val nextUrl = Uri.parse(nextUrlStr).getQueryParameter("next")

                Observable.just(response.body())
                    .concatWith(getPageAndNext(nextUrl!!.toInt()))
            }


    /**
     * Получение данных о требуемых домах по их полным именам (например фильтрация всех домов)
     * @param houseNames - массив полных названий домов (смотри AppConfig)
     * @param result - колбек содержащий в себе список данных о домах
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getNeedHouses(vararg houseNames: String, result: (houses: List<HouseRes>) -> Unit) {
        //TODO implement me
    }

    /**
     * Получение данных о требуемых домах по их полным именам и персонажах в каждом из домов
     * @param houseNames - массив полных названий домов (смотри AppConfig)
     * @param result - колбек содержащий в себе список данных о доме и персонажей в нем (Дом - Список Персонажей в нем)
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getNeedHouseWithCharacters(
        vararg houseNames: String,
        result: (houses: List<Pair<HouseRes, List<CharacterRes>>>) -> Unit
    ) {
        //TODO implement me
    }

    /**
     * Запись данных о домах в DB
     * @param houses - Список персонажей (модель HouseRes - модель ответа из сети)
     * необходимо произвести трансформацию данных
     * @param complete - колбек о завершении вставки записей db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun insertHouses(houses: List<HouseRes>, complete: () -> Unit) {
        //TODO implement me
    }

    /**
     * Запись данных о пересонажах в DB
     * @param Characters - Список персонажей (модель CharacterRes - модель ответа из сети)
     * необходимо произвести трансформацию данных
     * @param complete - колбек о завершении вставки записей db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun insertCharacters(Characters: List<CharacterRes>, complete: () -> Unit) {
        //TODO implement me
    }

    /**
     * При вызове данного метода необходимо выполнить удаление всех записей в db
     * @param complete - колбек о завершении очистки db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun dropDb(complete: () -> Unit) {
        //TODO implement me
    }

    /**
     * Поиск всех персонажей по имени дома, должен вернуть список краткой информации о персонажах
     * дома - смотри модель CharacterItem
     * @param name - краткое имя дома (его первычный ключ)
     * @param result - колбек содержащий в себе список краткой информации о персонажах дома
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun findCharactersByHouseName(name: String, result: (Characters: List<CharacterItem>) -> Unit) {
        //TODO implement me
    }

    /**
     * Поиск персонажа по его идентификатору, должен вернуть полную информацию о персонаже
     * и его родственных отношения - смотри модель CharacterFull
     * @param id - идентификатор персонажа
     * @param result - колбек содержащий в себе полную информацию о персонаже
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun findCharacterFullById(id: String, result: (Character: CharacterFull) -> Unit) {
        //TODO implement me
    }

    /**
     * Метод возвращет true если в базе нет ни одной записи, иначе false
     * @param result - колбек о завершении очистки db
     */
    fun isNeedUpdate(result: (isNeed: Boolean) -> Unit) {
        //TODO implement me
    }

}