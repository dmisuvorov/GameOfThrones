package ru.skillbranch.gameofthrones.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.local.entities.House
import ru.skillbranch.gameofthrones.data.local.entities.RelativeCharacter

@Database(entities = [CharacterItem::class, CharacterFull::class, RelativeCharacter::class, House::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GameOfThronesDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun houseDao(): HouseDao

    companion object {
        @Volatile
        private var instance: GameOfThronesDatabase? = null

        fun getInstance(context: Context): GameOfThronesDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): GameOfThronesDatabase {
            return Room.databaseBuilder(
                context,
                GameOfThronesDatabase::class.java,
                "gameOfThrones-db"
            ).build()
        }
    }
}