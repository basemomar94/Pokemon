package com.dadon.pokemon.ui.views.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dadon.pokemon.R
import com.dadon.pokemon.databinding.HomeFragmentBinding
import com.dadon.pokemon.models.Pokemon
import com.dadon.pokemon.ui.adapters.HomeAdapter
import com.dadon.pokemon.utilits.CONSTANTS.ID
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        test()
        if (isOnline(requireContext())) {
            gettingPokemons()
            observePokemons()
            searchInput()
        } else {
            Toast.makeText(requireContext(), "please check your connection", Toast.LENGTH_LONG)
                .show()
        }

    }


    private fun initViewModel() {
        viewModel = ViewModelProvider(this@HomeFragment)[PokemonViewModel::class.java]

    }

    private fun gettingPokemons() {
        try {
            uiScope.launch {
                viewModel?.getAllPokemon()
            }

        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun observePokemons() {
        viewModel?.allPokemon?.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                pokemonList = it
                settingRv(pokemonList)
            }

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

    override fun viewInfo(pokemon: Pokemon, position: Int) {
        gotoInfo(position, pokemon)
    }


    suspend fun makeFavorite(pokemon: Pokemon, position: Int) {
        val imageUrl =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/crystal/${position + 1}.png"
        pokemon.image = imageUrl
        uiScope.launch {
            viewModel?.addtoFavorite(pokemon)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    private fun gotoInfo(position: Int, pokemon: Pokemon) {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        var bundle = Bundle()
        bundle.putString("id", position.toString())
         bundle.putSerializable("pokemon", pokemon)
        navController.navigate(R.id.action_homeFragment_to_inoViewPager, bundle)
        ID = position.toString()
    }

    private fun test() {
        uiScope.launch {
            viewModel?.getPokemonInfo("5")

        }
    }
}

