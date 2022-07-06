package com.dadon.pokemon.ui.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dadon.pokemon.R
import com.dadon.pokemon.databinding.HomeFragmentBinding
import com.dadon.pokemon.models.Pokemon
import com.dadon.pokemon.ui.adapters.HomeAdapter
import com.dadon.pokemon.viewmodels.PokemonViewModel

class HomeFragment : Fragment(R.layout.home_fragment) {

    var binding: HomeFragmentBinding? = null
    var viewModel: PokemonViewModel? = null

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
    }


    private fun initViewModel() {
        viewModel = ViewModelProvider(this@HomeFragment)[PokemonViewModel::class.java]

    }

    private fun gettingPokemons() {
        viewModel?.getAllPokemon()
    }

    private fun observePokemons() {
        viewModel?.allPokemon?.observe(viewLifecycleOwner) {
            println(it.size)
            settingRv(it)
        }
    }


    private fun settingRv(_list: MutableList<Pokemon>) {
        binding?.pokemonRv?.apply {
            adapter = HomeAdapter(_list)
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }

    }

}