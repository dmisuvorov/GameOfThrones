package ru.skillbranch.gameofthrones

import android.app.Application
import android.content.Context
import ru.skillbranch.gameofthrones.di.ApplicationComponent
import ru.skillbranch.gameofthrones.di.DaggerApplicationComponent
import ru.skillbranch.gameofthrones.di.SplashSubComponent
import ru.skillbranch.gameofthrones.di.modules.AndroidModule
import ru.skillbranch.gameofthrones.di.modules.SplashModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        createComponent()
    }

    private fun createComponent() {
        component = DaggerApplicationComponent
            .builder()
            .androidModule(AndroidModule(this))
            .build()
    }

    companion object {
        lateinit var component: ApplicationComponent

        var splashSubComponent: SplashSubComponent? = null
            get() {
                field ?: return component.plus(SplashModule())
                return field
            }

        fun releaseSplashSubComponent() {
            splashSubComponent = null
        }

        fun get(context: Context): App = context.applicationContext as App
    }

}