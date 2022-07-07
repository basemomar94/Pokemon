package com.dadon.pokemon.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dadon.pokemon.databinding.PokemonItemBinding
import com.dadon.pokemon.models.Pokemon
import com.squareup.picasso.Picasso

class HomeAdapter(
    private var pokemonsList: MutableList<Pokemon>
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: PokemonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUrl =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/crystal/${position + 1}.png"
        val pokemon = pokemonsList[position]
        with(holder) {
            with(pokemonsList[position]) {
                Picasso.get().load(imageUrl).into(binding.pokeImg)
                binding.pokeName.text = this.name
            }
        }
    }

    override fun getItemCount() = pokemonsList.size

    fun search(searchList: MutableList<Pokemon>) {
        pokemonsList = searchList
        notifyDataSetChanged()
    }
}