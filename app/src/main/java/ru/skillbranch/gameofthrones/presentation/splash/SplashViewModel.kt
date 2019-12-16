package ru.skillbranch.gameofthrones.presentation.splash

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.repositories.RootRepository
import javax.inject.Inject


class SplashViewModel @Inject constructor() : ViewModel() {
    val state: MutableLiveData<SplashState> = MutableLiveData()

    init {
        state.value = SplashState.LOADING
    }

    @SuppressLint("VisibleForTests")
    fun getHouses(isConnected: Boolean) {
        RootRepository.isNeedUpdate { isNeed ->
            if (isNeed) {
                if (isConnected) {
                    RootRepository.getNeedHouseWithCharacters(*AppConfig.NEED_HOUSES) { needHousesWithCharacters ->
                        val lock = Object()
                        needHousesWithCharacters.map { it.first }
                            .also { RootRepository.insertHouses(it) { synchronized(lock) { lock.notify() } } }
                        synchronized(lock) { lock.wait() }

                        needHousesWithCharacters.map { it.second }.flatten()
                            .also { RootRepository.insertCharacters(it) { onDataSyncWithDatabase() } }
                    }
                } else {
                    onNotInternetConnected()
                }
            }
            onDataSyncWithDatabase()
        }
    }

    private fun onNotInternetConnected() {
        state.value = SplashState.ERROR(R.string.message_no_internet)
    }

    private fun onDataSyncWithDatabase() {
        state.value = SplashState.SUCCESS
    }

    sealed class SplashState {
        object LOADING : SplashState()
        object SUCCESS : SplashState()
        class ERROR(val errorMessage: Int) : SplashState()
    }
}