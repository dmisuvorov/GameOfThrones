package ru.skillbranch.gameofthrones.presentation.list

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.repositories.RootRepository
import javax.inject.Inject

class ListViewModel @Inject constructor() : ViewModel() {
    private val query = MutableLiveData<String>()
    private val characters = MutableLiveData<List<CharacterItem>>()

    @SuppressLint("VisibleForTests")
    fun getCharactersFromDb(houseTitle: String?) {
        RootRepository.findCharactersByHouseName(houseTitle!!) { characterItems ->
            characters.value = characterItems.sortedBy { it.name }
        }
    }

    fun getCharactersData(): LiveData<List<CharacterItem>> {
        val result = MediatorLiveData<List<CharacterItem>>()

        val filterF = {
            val queryStr = query.value ?: ""
            val charactersValue = characters.value ?: emptyList()

            result.value =
                if (queryStr.isEmpty()) charactersValue else charactersValue.filter {
                    it.name.contains(
                        queryStr,
                        true
                    )
                }
        }

        result.addSource(characters) { filterF.invoke() }
        result.addSource(query) { filterF.invoke() }

        return result
    }

    fun handleSearchQuery(text: String) {
        query.value = text
    }

}