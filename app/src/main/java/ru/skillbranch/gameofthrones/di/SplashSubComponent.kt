package ru.skillbranch.gameofthrones.di

import dagger.Subcomponent
import ru.skillbranch.gameofthrones.di.modules.SplashModule
import ru.skillbranch.gameofthrones.ui.splash.SplashScreen

@SplashScope
@Subcomponent(modules = [SplashModule::class])
interface SplashSubComponent {
    fun inject(splashScreen: SplashScreen)
}