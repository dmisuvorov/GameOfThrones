package ru.skillbranch.gameofthrones.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class CharacterViewModelFactory @Inject constructor() : ViewModelProvider.NewInstanceFactory() {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CharacterViewModel() as T
    }
}