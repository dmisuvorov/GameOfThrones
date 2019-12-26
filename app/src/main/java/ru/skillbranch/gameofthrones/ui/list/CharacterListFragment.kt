package ru.skillbranch.gameofthrones.ui.list

import android.content.Context
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_character_list.*
import ru.skillbranch.gameofthrones.App
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.presentation.list.ListViewModel
import ru.skillbranch.gameofthrones.ui.character.CharacterScreen
import ru.skillbranch.gameofthrones.ui.list.adapter.CharacterListAdapter
import ru.skillbranch.gameofthrones.util.convertDpToPx
import javax.inject.Inject

const val ARG_HOUSE = "house"

class CharacterListFragment : Fragment() {
    @Inject
    lateinit var viewModel: ListViewModel

    @Inject
    lateinit var applicationContext: Context

    private val recyclerAdapter = CharacterListAdapter { characterItem -> navigateToCharacterDetails(characterItem) }
    private var houseArgument: String? = null
    private var flag: Boolean =
        false //TODO: костыль onCreateOptionsMenu вызывается при свайпе страниц несколько раз что влечет несколько actionItem

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_character_list, container, false)
        setHasOptionsMenu(true)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (arguments != null) {
            houseArgument = arguments!!.getString(ARG_HOUSE)
            Log.d("M_CharacterListFragment", "onViewCreated $houseArgument")
            initViewModel()
            initViews()
            viewModel.getCharactersFromDb(houseArgument)
        }
    }

    override fun onResume() {
        super.onResume()
        flag = true
        Log.d("M_CharacterListFragment", "onResume $houseArgument")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (flag) {
            Log.d("M_CharacterListFragment", "onCreateOptionsMenu $houseArgument")
            inflater.inflate(R.menu.menu_search, menu)
            val searchItem = menu.findItem(R.id.search)
            val searchView = searchItem?.actionView as SearchView
            searchCharacter(searchView)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("M_CharacterListFragment", "onDestroyView $houseArgument")
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_CharacterListFragment", "onPause $houseArgument")
    }

    private fun searchCharacter(searchView: SearchView) {
        searchView.apply {
            queryHint = getString(R.string.hint_search)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.handleSearchQuery(query)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.handleSearchQuery(newText)
                    return true
                }

            })
        }
    }


    private fun initViewModel() {
        viewModel.getCharactersData().observe(viewLifecycleOwner, Observer { characters ->
            recyclerAdapter.submitList(characters)
        })
    }

    private fun initViews() {
        val customDivider = InsetDrawable(
            applicationContext.resources.getDrawable(R.drawable.divider, activity?.theme),
            convertDpToPx(72, applicationContext),
            0,
            0,
            0
        )
        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL).also {
            it.setDrawable(customDivider)
        }
        character_list.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(applicationContext)
            addItemDecoration(divider)
        }
    }

    private fun injectDependencies() {
        App.listSubComponent?.inject(this)
    }

    private fun navigateToCharacterDetails(characterItem: CharacterItem) {
        startActivity(CharacterScreen.newIntent(applicationContext, characterItem.id))
    }

    companion object {
        fun newInstance(house: String?): CharacterListFragment {
            val characterListFragment = CharacterListFragment()
            val bundle = Bundle().apply {
                putString(ARG_HOUSE, house)
            }
            characterListFragment.arguments = bundle
            return characterListFragment
        }
    }
}