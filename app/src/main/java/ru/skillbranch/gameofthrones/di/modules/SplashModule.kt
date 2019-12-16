package ru.skillbranch.gameofthrones.di.modules

import dagger.Module
import dagger.Provides
import ru.skillbranch.gameofthrones.di.SplashScope
import ru.skillbranch.gameofthrones.presentation.splash.SplashViewModel
import ru.skillbranch.gameofthrones.presentation.splash.SplashViewModelFactory

@Module
class SplashModule {
    @Provides
    @SplashScope
    fun provideSplashViewModelFactory(): SplashViewModelFactory = SplashViewModelFactory()

    @Provides
    @SplashScope
    fun provideSplashViewModel(splashViewModelFactory: SplashViewModelFactory): SplashViewModel =
        splashViewModelFactory.create(SplashViewModel::class.java)


}