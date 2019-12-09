package ru.skillbranch.gameofthrones.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character_item WHERE house = :name")
    fun findCharactersByHouseName(name : String): Flowable<List<CharacterItem>>

    @Query("SELECT * FROM character_full WHERE id = :id")
    fun findCharacterFullById(id : String): Single<CharacterFull>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharactersItem(characterItems: List<CharacterItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharactersFull(characterItems: List<CharacterFull>)
}