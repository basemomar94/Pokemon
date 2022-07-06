package com.dadon.pokemon.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dadon.pokemon.data.remote.RetrofitBuilder
import com.dadon.pokemon.data.remote.ServiceApi
import com.dadon.pokemon.models.Pokemon
import com.dadon.pokemon.repos.PokemonRepo
import kotlinx.coroutines.launch
import retrofit2.create

class PokemonViewModel : ViewModel() {
    private val api = RetrofitBuilder.getRetrofitBuilder().create(ServiceApi::class.java)
    private val repo = PokemonRepo(api)
    val allPokemon = MutableLiveData<MutableList<Pokemon>>()


    fun getAllPokemon() = viewModelScope.launch {
        allPokemon.postValue(repo.getAllPokemon().body()?.results)

    }

}