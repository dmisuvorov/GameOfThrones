package ru.skillbranch.gameofthrones.data.local

import androidx.room.*
import io.reactivex.Maybe
import io.reactivex.Single
import ru.skillbranch.gameofthrones.data.local.entities.Character
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.local.entities.RelativeCharacter

@Dao
interface CharacterDao {

    @Query("SELECT id, houseId as house, name, titles, aliases FROM characters WHERE houseId = :name")
    fun findCharactersByHouseName(name: String): Maybe<List<CharacterItem>>

    @Query("SELECT characters.id id, characters.name name, houses.words words, characters.born born, " +
            "characters.died died, characters.titles titles, characters.aliases aliases, characters.houseId house, " +
            "characters.father father, characters.mother mother " +
            "FROM characters, houses WHERE characters.id = :id AND houses.id = characters.houseId")
    fun findCharacterFullById(id: String): Maybe<CharacterFull>

    @Query("SELECT id, name, houseId as house FROM characters WHERE id = :id")
    fun findRelativeCharacterById(id: String): Single<RelativeCharacter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(character: List<Character>)

    @Query("SELECT COUNT(*) FROM characters")
    fun getCountEntity(): Single<Int>
}