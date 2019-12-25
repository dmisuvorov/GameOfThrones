package ru.skillbranch.gameofthrones.ui.list.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.skillbranch.gameofthrones.util.getMapHouseToFragmentPage

const val HOUSE_STARK_PAGE_INDEX = 0
const val HOUSE_LANNISTER_PAGE_INDEX = 1
const val HOUSE_TARGARYEN_PAGE_INDEX = 2
const val HOUSE_BARATHEON_PAGE_INDEX = 3
const val HOUSE_GREYJOY_PAGE_INDEX = 4
const val HOUSE_MARTELL_PAGE_INDEX = 5
const val HOUSE_TYRELL_PAGE_INDEX = 6

class HousesPageAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    private val tabFragmentCreators: Map<Int, () -> Fragment> =
        getMapHouseToFragmentPage(activity)

    override fun getItemCount(): Int = tabFragmentCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}