package ru.skillbranch.gameofthrones.di

import dagger.Component
import ru.skillbranch.gameofthrones.di.modules.AndroidModule
import ru.skillbranch.gameofthrones.di.modules.ClientModule
import ru.skillbranch.gameofthrones.di.modules.DatabaseModule
import ru.skillbranch.gameofthrones.di.modules.NetworkModule
import ru.skillbranch.gameofthrones.repositories.RootRepository
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidModule::class, NetworkModule::class, ClientModule::class, DatabaseModule::class]
)
interface RepositoryComponent {
    fun inject(rootRepository: RootRepository)
}