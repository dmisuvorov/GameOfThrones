package ru.skillbranch.gameofthrones.di

import dagger.Component
import ru.skillbranch.gameofthrones.di.modules.*
import ru.skillbranch.gameofthrones.ui.splash.SplashScreen
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidModule::class, NetworkModule::class, ClientModule::class, DatabaseModule::class]
)
interface ApplicationComponent {
    fun inject(splashScreen: SplashScreen)

    fun plus(splashModule: SplashModule): SplashSubComponent
}