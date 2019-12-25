package ru.skillbranch.gameofthrones.ui.list

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_character_list.*
import ru.skillbranch.gameofthrones.App
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.ui.list.adapter.*
import ru.skillbranch.gameofthrones.util.getHouseTabColor
import ru.skillbranch.gameofthrones.util.getHouseTabTitle
import javax.inject.Inject
import kotlin.math.hypot
import kotlin.math.max

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
                val rect = Rect()
                val tab = tabs.getTabAt(position)?.view as View
                tab.postDelayed(
                    {
                        tab.getGlobalVisibleRect(rect)
                        animateAppBarReveal(position, rect.centerX(), rect.centerY())
                    }, 300
                )
            }
        })
    }

    private fun animateAppBarReveal(position: Int, centerX: Int, centerY: Int) {
        val endRadius = max(
            hypot(centerX.toDouble(), centerY.toDouble()),
            hypot(app_bar_layout.width.toDouble() - centerX.toDouble(), centerY.toDouble())
        )

        with(reveal_view) {
            visibility = View.VISIBLE
            setBackgroundColor(getColor(getTabColor(position)))
        }

        ViewAnimationUtils.createCircularReveal(
            reveal_view,
            centerX,
            centerY,
            0F,
            endRadius.toFloat()
        ).apply {
            doOnEnd {
                app_bar_layout.background = ColorDrawable(getColor(getTabColor(position)))
                reveal_view.visibility = View.INVISIBLE
            }
            start()
        }
    }

    private fun getTabColor(position: Int): Int =
        getHouseTabColor(position)

    private fun getTabTitle(position: Int): String? =
        getHouseTabTitle(context, position)

    private fun releaseSubComponent() {
        App.releaseListSubComponent()
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, CharacterListScreen::class.java)
    }

}