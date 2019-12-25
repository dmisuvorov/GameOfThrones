package ru.skillbranch.gameofthrones.di

import dagger.Subcomponent
import ru.skillbranch.gameofthrones.di.modules.CharacterModule
import ru.skillbranch.gameofthrones.ui.character.CharacterScreen

@CharacterScope
@Subcomponent(modules = [CharacterModule::class])
interface CharacterSubComponent {
    fun inject(characterScreen: CharacterScreen)
}