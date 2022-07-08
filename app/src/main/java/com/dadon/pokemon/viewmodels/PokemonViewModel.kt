package com.dadon.pokemon.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dadon.pokemon.data.remote.RetrofitBuilder
import com.dadon.pokemon.data.remote.ServiceApi
import com.dadon.pokemon.models.Description
import com.dadon.pokemon.models.Pokemon
import com.dadon.pokemon.repos.PokemonRepo
import kotlinx.coroutines.launch
import retrofit2.create

class PokemonViewModel(app: Application) : AndroidViewModel(app) {
    private val context by lazy { app.applicationContext }
    private val api = RetrofitBuilder.getRetrofitBuilder().create(ServiceApi::class.java)
    private val repo = PokemonRepo(api, context)
    val allPokemon = MutableLiveData<MutableList<Pokemon>>()
    val favPokemon = MutableLiveData<MutableList<Pokemon>>()
    val pokemonDesc = MutableLiveData<Description>()


    suspend fun getAllPokemon() = viewModelScope.launch {
        allPokemon.postValue(repo.getAllPokemon().body()?.results)
    }

    suspend fun addtoFavorite(pokemon: Pokemon) = viewModelScope.launch {
        repo.addFavorite(pokemon)
    }

    suspend fun getFav() {
        repo.getFavorite(favPokemon)
    }

    suspend fun removeFav(pokemon: Pokemon) {
        repo.removeFavorite(pokemon)
    }

    suspend fun getPokemonInfo(id: String) {
        pokemonDesc.postValue(repo.getPokemonInfo(id).body())



    }

}