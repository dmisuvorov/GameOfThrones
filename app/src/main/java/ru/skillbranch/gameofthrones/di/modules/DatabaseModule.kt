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
    fun provideCharacterDao(context: Context) : CharacterDao = GameOfThronesDatabase.getInstance(context).characterDao()

    @Provides
    @Singleton
    fun provideHouseDao(context: Context) : HouseDao = GameOfThronesDatabase.getInstance(context).houseDao()
}