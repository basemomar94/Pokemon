package com.dadon.pokemon.repos

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.dadon.pokemon.data.local.PokemonDatabase
import com.dadon.pokemon.data.remote.ServiceApi
import com.dadon.pokemon.models.Description
import com.dadon.pokemon.models.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepo(private val serviceApi: ServiceApi, private val context: Context) {

    val db = PokemonDatabase.getInstance(context)

    suspend fun getAllPokemon() = withContext(Dispatchers.IO) {
        serviceApi.getAllPokemon("150")
    }

    suspend fun getPokemonInfo(id: String) = withContext(Dispatchers.IO) {
        serviceApi.getPokemonInfo(id)
    }


    suspend fun addFavorite(pokemon: Pokemon) {
        db.pokemonDao().addFavorite(pokemon)
    }

    suspend fun getFavorite(_list: MutableLiveData<MutableList<Pokemon>>) {
        _list.postValue(db.pokemonDao().getFavorites())
    }

    suspend fun removeFavorite(pokemon: Pokemon) {
        db.pokemonDao().removeFavorite(pokemon)
    }


}