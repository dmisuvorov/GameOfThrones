package ru.skillbranch.gameofthrones.presentation.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.gameofthrones.repositories.RootRepository
import javax.inject.Inject


class SplashViewModel @Inject constructor() : ViewModel() {
    val state: MutableLiveData<SplashState> = MutableLiveData()

    init {
        state.value = SplashState.LOADING
    }

    fun getHouses() {
       RootRepository.getAllHouses { houses -> state.value = SplashState.SUCCESS }
    }

    sealed class SplashState {
        object LOADING : SplashState()
        object SUCCESS : SplashState()
        class ERROR(val errorMessage: Int) : SplashState()
    }
}