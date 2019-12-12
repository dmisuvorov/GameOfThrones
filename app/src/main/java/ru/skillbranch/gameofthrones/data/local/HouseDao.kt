package ru.skillbranch.gameofthrones.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import ru.skillbranch.gameofthrones.data.local.entities.House

@Dao
interface HouseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHouses(houseItems: List<House>)

    @Query("SELECT COUNT(*) FROM houses")
    fun getCountEntity(): Single<Int>
}