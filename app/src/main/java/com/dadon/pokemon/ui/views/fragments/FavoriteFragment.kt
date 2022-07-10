package com.dadon.pokemon.ui.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dadon.pokemon.R
import com.dadon.pokemon.databinding.FavoriteFragmentBinding
import com.dadon.pokemon.models.Pokemon
import com.dadon.pokemon.ui.adapters.FavoriteAdapter
import com.dadon.pokemon.viewmodels.PokemonViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*

class FavoriteFragment : Fragment(R.layout.favorite_fragment) {

    var binding: FavoriteFragmentBinding? = null
    var viewModel: PokemonViewModel? = null
    private var favoriteAdapter: FavoriteAdapter? = null
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private var favList: MutableList<Pokemon> = mutableListOf()


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

    private fun observeFav() {
        viewModel?.favPokemon?.observe(viewLifecycleOwner) {
            if (it != null) {
                favList = it
                settingRv(favList)
                swipeDelete()
            }


        }
    }

    private fun settingRv(_list: MutableList<Pokemon>) {
        favoriteAdapter = FavoriteAdapter(_list)
        binding?.favoriteRv?.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

    }

    private fun swipeDelete() {
        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val pokemon = favList?.get(position)
                    if (pokemon != null) {
                        deletePokemon(pokemon, position)
                    }


                }

            }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding?.favoriteRv)

    }

    private fun deletePokemon(pokemon: Pokemon, position: Int) {
        favList.remove(pokemon)
        favoriteAdapter?.notifyItemRemoved(position)
        uiScope.launch {
            viewModel?.removeFav(pokemon)
        }

        /* Snackbar.make(requireView(), "You have remove a Pokemon", Snackbar.LENGTH_LONG)
             .setAction("undo") {
                 undoDelete(pokemon, position)
             }.show()*/
        showSnackBar(pokemon, position)

    }

    private fun undoDelete(pokemon: Pokemon, position: Int) {
        uiScope.launch {
            viewModel?.addtoFavorite(pokemon)
        }
        favList.add(position, pokemon)
        favoriteAdapter?.notifyItemInserted(position)

    }

    private fun showSnackBar(pokemon: Pokemon, position: Int) {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomAppBar)
        val snackbar =
            Snackbar.make(requireView(), "You've removed from favorite", Snackbar.LENGTH_LONG)
        snackbar.anchorView = bottomNavigationView
        snackbar.setAction("undo") {
            undoDelete(pokemon, position)
        }
        snackbar.show()

    }
}