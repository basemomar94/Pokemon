package com.dadon.pokemon.repos

import com.dadon.pokemon.data.remote.ServiceApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepo (private val serviceApi: ServiceApi){


     suspend fun getAllPokemon() = withContext(Dispatchers.IO){
          serviceApi.getAllPokemon("150")
    }


}