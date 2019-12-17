package ru.skillbranch.gameofthrones.ui.splash

import android.content.Context
import android.os.Bundle
import android.view.animation.AnimationUtils

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import ru.skillbranch.gameofthrones.App
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.presentation.splash.SplashViewModel
import ru.skillbranch.gameofthrones.ui.list.CharacterListScreen
import ru.skillbranch.gameofthrones.util.isConnected
import javax.inject.Inject


class SplashScreen : AppCompatActivity(), SplashView {
    @Inject
    lateinit var splashViewModel: SplashViewModel

    @Inject
    lateinit var context: Context


    private val splashImg by lazy { findViewById<ImageView>(R.id.iv_splash) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        injectDependencies()
        initViewModels()
        loadAnimations()
    }

    private fun initViewModels() {
        splashViewModel.state.observe(this, Observer { state ->
            when (state) {
                is SplashViewModel.SplashState.LOADING -> onStartLoading()
                is SplashViewModel.SplashState.ERROR -> showErrorMessage(state.errorMessage)
                is SplashViewModel.SplashState.SUCCESS -> navigateToCharacterList()
            }
        })
    }

    private fun onStartLoading() {
        splashViewModel.getHouses(isConnected())
    }

    override fun navigateToCharacterList() {
        startActivity(CharacterListScreen.newIntent(context))
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseSubComponent()
    }

    private fun releaseSubComponent() {
        App.releaseSplashSubComponent()
    }

    private fun injectDependencies() {
        App.splashSubComponent?.inject(this)
    }

    private fun loadAnimations() {
        val anim = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        splashImg.startAnimation(anim)
    }

    override fun showErrorMessage(message: Int) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
            .show()
    }
}
