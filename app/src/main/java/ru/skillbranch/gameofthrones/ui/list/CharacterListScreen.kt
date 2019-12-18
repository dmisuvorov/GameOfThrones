package ru.skillbranch.gameofthrones.ui.list

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
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
    }


    override fun onDestroy() {
        super.onDestroy()
        releaseSubComponent()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }

    private fun injectDependencies() {
        App.component.inject(this)
    }

    private fun initViewPager() {
        view_pager.adapter = HousesPageAdapter(this)
        TabLayoutMediator(tabs, view_pager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
        view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                app_bar_layout.background = ColorDrawable(context.getColor(getTabColor(position)))
            }
        })
    }

    private fun getTabColor(position: Int): Int = getHouseTabColor(position)

    private fun getTabTitle(position: Int): String? = getHouseTabTitle(context, position)

    private fun releaseSubComponent() {
        App.releaseSplashSubComponent()
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, CharacterListScreen::class.java)
    }

}