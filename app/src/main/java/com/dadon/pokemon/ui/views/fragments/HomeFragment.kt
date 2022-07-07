package com.dadon.pokemon.ui.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.dadon.pokemon.R
import com.dadon.pokemon.databinding.HomeFragmentBinding
import com.dadon.pokemon.models.Pokemon
import com.dadon.pokemon.ui.adapters.HomeAdapter
import com.dadon.pokemon.viewmodels.PokemonViewModel
import kotlinx.coroutines.*

class HomeFragment : Fragment(R.layout.home_fragment), HomeAdapter.InteractInterface {

    private var binding: HomeFragmentBinding? = null
    private var viewModel: PokemonViewModel? = null
    private var homeAdapter: HomeAdapter? = null
    private var pokemonList: MutableList<Pokemon> = mutableListOf()
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        gettingPokemons()
        observePokemons()
        searchInput()
    }


    private fun initViewModel() {
        viewModel = ViewModelProvider(this@HomeFragment)[PokemonViewModel::class.java]

    }

    private fun gettingPokemons() {
        viewModel?.getAllPokemon()
    }

    private fun observePokemons() {
        viewModel?.allPokemon?.observe(viewLifecycleOwner) {
            pokemonList = it
            settingRv(pokemonList)
        }
    }


    private fun settingRv(_list: MutableList<Pokemon>) {
        homeAdapter = HomeAdapter(_list, this)
        binding?.pokemonRv?.apply {
            adapter = homeAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }

    }


    private fun searchInput() {
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {

                return false

            }

            override fun onQueryTextChange(query: String?): Boolean {

                query?.let { searchProcess(it) }
                return true
            }
        })
    }


    private fun searchProcess(search: String) {
        val searchList: MutableList<Pokemon> = mutableListOf()
        pokemonList.forEach {
            if (it.name.contains(search)) {
                searchList.add(it)
            }
        }

        homeAdapter?.search(searchList)

    }

    override fun makeFav(pokemon: Pokemon, position: Int) {
        uiScope.launch {
            makeFavorite(pokemon, position)
        }


    }


    suspend fun makeFavorite(pokemon: Pokemon, position: Int) {
        val imageUrl =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/crystal/${position + 1}.png"
        pokemon.image = imageUrl
        withContext(Dispatchers.IO) {
            viewModel?.addtoFavorite(pokemon)
        }

    }

}