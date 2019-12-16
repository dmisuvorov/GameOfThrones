package ru.skillbranch.gameofthrones.presentation.list

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.repositories.RootRepository
import javax.inject.Inject

class ListViewModel @Inject constructor() : ViewModel() {

    val characters: MutableLiveData<List<CharacterItem>> = MutableLiveData()

    @SuppressLint("VisibleForTests")
    fun getCharacters(houseTitle: String?) {
        RootRepository.findCharactersByHouseName(houseTitle!!) {
            characters.value = it
        }
    }

}