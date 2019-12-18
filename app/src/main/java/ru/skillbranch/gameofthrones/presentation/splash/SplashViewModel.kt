package ru.skillbranch.gameofthrones.presentation.splash

import android.annotation.SuppressLint
import android.os.Handler
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
                        needHousesWithCharacters.map { it.first }
                            .also { houses ->
                                RootRepository.insertHouses(houses) {
                                    needHousesWithCharacters.map { it.second }.flatten()
                                        .also { RootRepository.insertCharacters(it) { onDataSyncWithDatabase() } }
                                }
                            }
                    }
                } else {
                    onNotInternetConnected()
                }
            } else {
                Handler().postDelayed({ onDataSyncWithDatabase() }, 5000)
            }
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