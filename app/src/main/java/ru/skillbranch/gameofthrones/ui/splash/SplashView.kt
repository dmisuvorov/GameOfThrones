package ru.skillbranch.gameofthrones.ui.splash

interface SplashView {
    fun showErrorMessage(message: Int)

    fun navigateToCharacterList()
}