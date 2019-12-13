package ru.skillbranch.gameofthrones.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.skillbranch.gameofthrones.data.local.CharacterDao
import ru.skillbranch.gameofthrones.data.local.GameOfThronesDatabase
import ru.skillbranch.gameofthrones.data.local.HouseDao
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): GameOfThronesDatabase =
        GameOfThronesDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideCharacterDao(gameOfThronesDatabase: GameOfThronesDatabase): CharacterDao =
        gameOfThronesDatabase.characterDao()

    @Provides
    @Singleton
    fun provideHouseDao(gameOfThronesDatabase: GameOfThronesDatabase): HouseDao =
        gameOfThronesDatabase.houseDao()
}