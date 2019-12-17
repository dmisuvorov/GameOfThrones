package ru.skillbranch.gameofthrones.ui.list

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_character_list.*
import kotlinx.android.synthetic.main.fragment_character_list.*
import ru.skillbranch.gameofthrones.App
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.presentation.list.ListViewModel
import ru.skillbranch.gameofthrones.ui.list.adapter.CharacterListAdapter
import javax.inject.Inject

const val ARG_HOUSE = "house"

class CharacterListFragment : Fragment() {
    @Inject
    lateinit var viewModel: ListViewModel

    private val adapter = CharacterListAdapter({})
    private var houseArgument: String? = null

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
            initViews()
            initViewModel()
            viewModel.getCharacters(houseArgument)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_search -> {
                searchCharacter(item as SearchView)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun searchCharacter(searchView: SearchView): Boolean {
        searchView.apply {
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

    private fun initViewModel() {
        viewModel.characters.observe(viewLifecycleOwner, Observer { characters ->
            adapter.submitList(characters)
        })
    }

    private fun initViews() {
        character_list.adapter = adapter
    }

    private fun injectDependencies() {
        App.listSubComponent?.inject(this)
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