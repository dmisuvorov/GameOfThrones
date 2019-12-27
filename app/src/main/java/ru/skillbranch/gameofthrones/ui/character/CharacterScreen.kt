package ru.skillbranch.gameofthrones.ui.character

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_character.*
import ru.skillbranch.gameofthrones.App
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.presentation.character.CharacterViewModel
import ru.skillbranch.gameofthrones.util.getColorAccentHouse
import ru.skillbranch.gameofthrones.util.getColorDarkHouse
import ru.skillbranch.gameofthrones.util.getColorPrimaryHouse
import ru.skillbranch.gameofthrones.util.getDrawableHouseCoastOfArms
import javax.inject.Inject

const val ARG_CHARACTER_ID = "arg_character_id"

class CharacterScreen : AppCompatActivity() {

    @Inject
    lateinit var characterViewModel: CharacterViewModel

    @Inject
    lateinit var appContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        injectDependencies()
        if (intent == null || intent.extras == null || intent.extras!!.getString(ARG_CHARACTER_ID) == null) {
            finish()
            return
        }
        savedInstanceState ?: initViewModel(intent.extras!!.getString(ARG_CHARACTER_ID)!!)
        setSupportActionBar(toolbar_character_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_character_details.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initViewModel(characterId: String) {
        characterViewModel.characterFull.observe(this, Observer { initViews(it) })
        characterViewModel.getCharacterFullById(characterId)
    }

    private fun initViews(characterFull: CharacterFull) {
        val scrim = getColorPrimaryHouse(characterFull.house)
        val darkScrim = getColorDarkHouse(characterFull.house)
        val iconColor = getColorAccentHouse(characterFull.house)

        toolbar_layout_character_details.apply {
            setBackgroundResource(scrim!!)
            setContentScrimResource(scrim)
            setStatusBarScrimColor(darkScrim!!)
        }

        Glide.with(this)
            .load(getDrawableHouseCoastOfArms(characterFull.house))
            .fitCenter()
            .into(iv_coast_of_arms_character_details)
        toolbar_layout_character_details.title = characterFull.name
        tv_words.text = characterFull.words
        tv_born.text = characterFull.born
        tv_titles.text = characterFull.titles.filter { it.isNotEmpty() }.joinToString (",\n")
        tv_aliases.text = characterFull.aliases.filter { it.isNotEmpty() }.joinToString (",\n")

        listOf(tv_header_words, tv_header_born, tv_header_titles, tv_header_aliases)
            .forEach {
                it.compoundDrawables.first().setTint(iconColor!!)
            }

        characterFull.father?.takeIf { it.id.isNotEmpty() }?.let { rel ->
            group_father.visibility = View.VISIBLE
            btn_father.text = rel.name
            btn_father.background  = ColorDrawable(getColor(scrim!!))
            btn_father.setOnClickListener { startActivity(newIntent(appContext, rel.id)) }
        }

        characterFull.mother?.takeIf { it.id.isNotEmpty() }?.let { rel ->
            group_mother.visibility = View.VISIBLE
            btn_mother.text = rel.name
            btn_mother.background  = ColorDrawable(getColor(scrim!!))
            btn_mother.setOnClickListener { startActivity(newIntent(appContext, rel.id)) }
        }

        if (characterFull.died.isNotEmpty()) {
            Snackbar.make(
                coordinator_character_details,
                "Died is : ${characterFull.died}",
                Snackbar.LENGTH_INDEFINITE
            ).show()
        }
    }

    private fun injectDependencies() {
        App.characterSubComponent?.inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseSubComponent()
    }

    private fun releaseSubComponent() {
        App.releaseCharacterSubComponent()
    }

    companion object {
        fun newIntent(context: Context, characterId: String) =
            Intent(context, CharacterScreen::class.java).apply {
                putExtra(ARG_CHARACTER_ID, characterId)
            }
    }
}