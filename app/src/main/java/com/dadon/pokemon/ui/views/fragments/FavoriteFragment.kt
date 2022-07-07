package com.dadon.pokemon.ui.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dadon.pokemon.R
import com.dadon.pokemon.databinding.FavoriteFragmentBinding
import com.dadon.pokemon.models.Pokemon
import com.dadon.pokemon.ui.adapters.FavoriteAdapter
import com.dadon.pokemon.ui.adapters.HomeAdapter
import com.dadon.pokemon.viewmodels.PokemonViewModel
import kotlinx.coroutines.*

class FavoriteFragment : Fragment(R.layout.favorite_fragment) {

    var binding: FavoriteFragmentBinding? = null
    var viewModel: PokemonViewModel? = null
    private var favoriteAdapter: FavoriteAdapter? = null
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
        binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        getFav()
        getFav()
        observeFav()

    }


    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[PokemonViewModel::class.java]

    }

    private fun getFav() {
        uiScope.launch {
            viewModel?.getFav()
        }
    }

    private fun observeFav(){
        viewModel?.favPokemon?.observe(viewLifecycleOwner){
            settingRv(it)

        }
    }

    private fun settingRv(_list: MutableList<Pokemon>) {
        favoriteAdapter = FavoriteAdapter(_list)
        binding?.favoriteRv?.apply {
            adapter = favoriteAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }

    }
}