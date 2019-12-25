package ru.skillbranch.gameofthrones.di.modules

import dagger.Module
import dagger.Provides
import ru.skillbranch.gameofthrones.di.CharacterScope
import ru.skillbranch.gameofthrones.presentation.character.CharacterViewModel
import ru.skillbranch.gameofthrones.presentation.character.CharacterViewModelFactory

@Module
class CharacterModule {

    @Provides
    @CharacterScope
    fun provideCharacterViewModelFactory(): CharacterViewModelFactory = CharacterViewModelFactory()

    @Provides
    @CharacterScope
    fun provideCharacterViewModel(characterViewModelFactory: CharacterViewModelFactory): CharacterViewModel =
        characterViewModelFactory.create(CharacterViewModel::class.java)
}