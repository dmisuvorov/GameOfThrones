package ru.skillbranch.gameofthrones.ui.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                searchCharacter(item as SearchView)
                return true
            }
            else -> true
        }
    }

    private fun searchCharacter(searchView: SearchView): Boolean {
        searchView.apply {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            queryHint = getString(R.string.hint_search)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
        }
        return true
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