package ru.skillbranch.gameofthrones.ui.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
    ): View? = inflater.inflate(R.layout.fragment_character_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (arguments != null) {
            houseArgument = arguments!!.getString(ARG_HOUSE)
            initViews()
            initViewModel()
            viewModel.getCharacters(houseArgument)
        }
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