package ru.skillbranch.gameofthrones.ui.character

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_character.*
import kotlinx.android.synthetic.main.activity_character_list.*
import ru.skillbranch.gameofthrones.App
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.presentation.character.CharacterViewModel
import ru.skillbranch.gameofthrones.util.getColorPrimaryHouse
import ru.skillbranch.gameofthrones.util.getDrawableHouseCoastOfArms
import javax.inject.Inject

const val ARG_CHARACTER_ID = "arg_character_id"

class CharacterScreen : AppCompatActivity() {

    @Inject
    lateinit var characterViewModel: CharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        injectDependencies()
        if (intent == null || intent.extras == null || intent.extras!!.getString(ARG_CHARACTER_ID) == null) {
            finish()
            return
        }
        savedInstanceState ?: initViewModel(intent.extras!!.getString(ARG_CHARACTER_ID)!!)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun initViewModel(characterId: String) {
        characterViewModel.characterFull.observe(this, Observer { initViews(it) })
        characterViewModel.getCharacterFullById(characterId)
    }

    private fun initViews(characterFull: CharacterFull) {
        toolbar_layout_character_details.setContentScrimResource(getColorPrimaryHouse(characterFull.house)!!)
        Glide.with(this)
            .load(getDrawableHouseCoastOfArms(characterFull.house))
            .into(iv_coast_of_arms_character_details)
        toolbar_layout_character_details.title = characterFull.name
        tv_words.text = characterFull.words
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