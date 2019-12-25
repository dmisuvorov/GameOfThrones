package ru.skillbranch.gameofthrones.presentation.character

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.repositories.RootRepository

class CharacterViewModel : ViewModel() {
    val characterFull = MutableLiveData<CharacterFull>()

    @SuppressLint("VisibleForTests")
    fun getCharacterFullById(id: String) {
        RootRepository.findCharacterFullById(id) { character ->
            characterFull.value = character
        }
    }

}
