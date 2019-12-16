package ru.skillbranch.gameofthrones.di

import dagger.Subcomponent
import ru.skillbranch.gameofthrones.di.modules.ListModule
import ru.skillbranch.gameofthrones.ui.list.CharacterListFragment

@ListScope
@Subcomponent(modules = [ListModule::class])
interface ListSubComponent {
    fun inject(characterListFragment: CharacterListFragment)
}