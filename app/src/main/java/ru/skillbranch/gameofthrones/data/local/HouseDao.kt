package ru.skillbranch.gameofthrones.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ru.skillbranch.gameofthrones.data.local.entities.House

@Dao
interface HouseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHouses(characterItems: List<House>)
}