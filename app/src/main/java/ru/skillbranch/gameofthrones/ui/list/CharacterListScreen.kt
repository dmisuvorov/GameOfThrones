package ru.skillbranch.gameofthrones.ui.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_character_list.*
import ru.skillbranch.gameofthrones.App
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.ui.list.adapter.*
import javax.inject.Inject

class CharacterListScreen : AppCompatActivity() {
    @Inject
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)
        injectDependencies()
        initViewPager()

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        releaseSubComponent()
    }

    private fun injectDependencies() {
        App.component.inject(this)
    }

    private fun initViewPager() {
        view_pager.adapter = HousesPageAdapter(this)
        TabLayoutMediator(tabs, view_pager) { tab, position ->
            app_bar_layout.setBackgroundColor(getTabColor(position))
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabColor(position: Int): Int = getHouseTabColor(context, position)

    private fun getTabTitle(position: Int): String? = getHouseTabTitle(context, position)

    private fun releaseSubComponent() {
        App.releaseSplashSubComponent()
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, CharacterListScreen::class.java)
    }

}