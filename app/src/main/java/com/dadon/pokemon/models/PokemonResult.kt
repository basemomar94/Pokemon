package com.dadon.pokemon.models

data class PokemonResult(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: MutableList<Pokemon>
)