package ru.skillbranch.gameofthrones.ui.list

import android.content.Context
import androidx.core.content.ContextCompat
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.ui.list.adapter.*

fun getHouseTabColor(context: Context, position: Int): Int {
    return when (position) {
        HOUSE_STARK_PAGE_INDEX -> ContextCompat.getColor(context, R.color.stark_primary)
        HOUSE_LANNISTER_PAGE_INDEX -> ContextCompat.getColor(context, R.color.lannister_primary)
        HOUSE_TARGARYEN_PAGE_INDEX -> ContextCompat.getColor(context, R.color.targaryen_primary)
        HOUSE_BARATHEON_PAGE_INDEX -> ContextCompat.getColor(context, R.color.baratheon_primary)
        HOUSE_GREYJOY_PAGE_INDEX -> ContextCompat.getColor(context, R.color.greyjoy_primary)
        HOUSE_MARTELL_PAGE_INDEX -> ContextCompat.getColor(context, R.color.martel_primary)
        HOUSE_TYRELL_PAGE_INDEX -> ContextCompat.getColor(context, R.color.tyrel_primary)
        else -> throw IndexOutOfBoundsException()
    }
}

fun getHouseTabTitle(context: Context, position: Int): String? {
    return when (position) {
        HOUSE_STARK_PAGE_INDEX -> context.getString(R.string.stark_title)
        HOUSE_LANNISTER_PAGE_INDEX -> context.getString(R.string.lannister_title)
        HOUSE_TARGARYEN_PAGE_INDEX -> context.getString(R.string.targaryen_title)
        HOUSE_BARATHEON_PAGE_INDEX -> context.getString(R.string.baratheon_title)
        HOUSE_GREYJOY_PAGE_INDEX -> context.getString(R.string.greyjoy_title)
        HOUSE_MARTELL_PAGE_INDEX -> context.getString(R.string.martel_title)
        HOUSE_TYRELL_PAGE_INDEX -> context.getString(R.string.tyrell_title)
        else -> null
    }
}

fun getMapHouseToFragmentPage(context: Context) = mapOf(
    HOUSE_STARK_PAGE_INDEX to {
        CharacterListFragment.newInstance(
            getHouseTabTitle(context, HOUSE_STARK_PAGE_INDEX)
        )
    },
    HOUSE_LANNISTER_PAGE_INDEX to {
        CharacterListFragment.newInstance(
            getHouseTabTitle(context, HOUSE_LANNISTER_PAGE_INDEX)
        )
    },
    HOUSE_TARGARYEN_PAGE_INDEX to {
        CharacterListFragment.newInstance(
            getHouseTabTitle(context, HOUSE_TARGARYEN_PAGE_INDEX)
        )
    },
    HOUSE_BARATHEON_PAGE_INDEX to {
        CharacterListFragment.newInstance(
            getHouseTabTitle(context, HOUSE_BARATHEON_PAGE_INDEX)
        )
    },
    HOUSE_GREYJOY_PAGE_INDEX to {
        CharacterListFragment.newInstance(
            getHouseTabTitle(context, HOUSE_GREYJOY_PAGE_INDEX)
        )
    },
    HOUSE_MARTELL_PAGE_INDEX to {
        CharacterListFragment.newInstance(
            getHouseTabTitle(context, HOUSE_MARTELL_PAGE_INDEX)
        )
    },
    HOUSE_TYRELL_PAGE_INDEX to {
        CharacterListFragment.newInstance(
            getHouseTabTitle(context, HOUSE_TYRELL_PAGE_INDEX)
        )
    }
)

fun getDrawableHouseIcon(house: String): Int? {
    return when (house) {
        "Stark" -> R.drawable.stark_icon
        "Lannister" -> R.drawable.lannister_icon
        "Targaryen" -> R.drawable.targaryen_icon
        "Baratheon" -> R.drawable.baratheon_icon
        "Greyjoy" -> R.drawable.greyjoy_icon
        "Martell" -> R.drawable.martel_icon
        "Tyrell" -> R.drawable.tyrel_icon
        else -> null
    }
}