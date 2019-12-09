package ru.skillbranch.gameofthrones.di.modules

import dagger.Module
import ru.skillbranch.gameofthrones.presentation.splash.SplashViewModelFactory

@Module
class SplashModule {
    fun provideSplashViewModelFactory(): SplashViewModelFactory = SplashViewModelFactory()
}