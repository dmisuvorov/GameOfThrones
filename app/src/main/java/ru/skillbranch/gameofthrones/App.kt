package ru.skillbranch.gameofthrones

import android.app.Application
import android.content.Context
import ru.skillbranch.gameofthrones.di.ApplicationComponent
import ru.skillbranch.gameofthrones.di.DaggerApplicationComponent
import ru.skillbranch.gameofthrones.di.SplashSubComponent
import ru.skillbranch.gameofthrones.di.modules.AndroidModule
import ru.skillbranch.gameofthrones.di.modules.SplashModule

class App : Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .builder()
            .androidModule(AndroidModule(this))
            .build()
    }

    var splashSubComponent: SplashSubComponent? = null
        get() {
            field ?: return component.plus(SplashModule())
            return field
        }


    fun releaseSplashSubComponent() {
        splashSubComponent = null
    }


    companion object {
        fun get(context: Context): App = context.applicationContext as App
    }

}