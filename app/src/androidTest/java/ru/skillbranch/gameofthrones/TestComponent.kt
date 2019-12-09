package ru.skillbranch.gameofthrones

import dagger.Component
import ru.skillbranch.gameofthrones.di.ApplicationComponent
import ru.skillbranch.gameofthrones.di.modules.AndroidModule
import ru.skillbranch.gameofthrones.di.modules.ClientModule
import ru.skillbranch.gameofthrones.di.modules.DatabaseModule
import ru.skillbranch.gameofthrones.di.modules.NetworkModule
import javax.inject.Singleton

//@Singleton
//@Component(
//    modules = [NetworkModule::class, ClientModule::class, DatabaseModule::class]
//)
interface TestComponent : ApplicationComponent {
//    fun inject(instrumentedTest: ExampleInstrumentedTest)
}