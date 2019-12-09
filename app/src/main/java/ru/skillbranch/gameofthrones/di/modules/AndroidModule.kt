package ru.skillbranch.gameofthrones.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.skillbranch.gameofthrones.App
import javax.inject.Singleton

@Module
class AndroidModule(private val application: App) {

    @Provides
    @Singleton
    fun provideContext(): Context = application.applicationContext


}