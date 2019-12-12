package ru.skillbranch.gameofthrones.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import ru.skillbranch.gameofthrones.data.local.entities.Character
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem

@Dao
interface CharacterDao {

    @Query("SELECT id, houseId as house, name, titles, aliases FROM characters WHERE houseId = :name")
    fun findCharactersByHouseName(name : String): Single<List<CharacterItem>>
//
//    @Query("SELECT * FROM character_full WHERE id = :id")
//    fun findCharacterFullById(id : String): Single<CharacterFull>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(character: List<Character>)

    @Query("SELECT COUNT(*) FROM characters")
    fun getCountEntity(): Single<Int>
}