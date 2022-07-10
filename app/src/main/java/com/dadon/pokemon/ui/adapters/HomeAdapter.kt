package com.dadon.pokemon.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.dadon.pokemon.R
import com.dadon.pokemon.databinding.PokemonItemBinding
import com.dadon.pokemon.models.Pokemon
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class HomeAdapter(
    private var pokemonsList: MutableList<Pokemon>,
    var onclick: InteractInterface,
    val context: Context
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: PokemonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imageView.setOnClickListener {
                val pokemon = pokemonsList[adapterPosition]
                onclick.makeFav(pokemon, adapterPosition)
            }

            itemView?.setOnClickListener {
                val pokemon = pokemonsList[adapterPosition]

                onclick.viewInfo(pokemon, adapterPosition)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorite = AppCompatResources.getDrawable(context, R.drawable.ic_red_favorite_24)
        val unfavorite =
            AppCompatResources.getDrawable(context, R.drawable.ic_baseline_favorite_border_24)


        val imageUrl =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/crystal/${position + 1}.png"
        val pokemon = pokemonsList[position]
        with(holder) {
            with(pokemonsList[position]) {
                //   Picasso.get().load(imageUrl).into(binding.pokeImg)
                Picasso.get().load(imageUrl).fetch()
                Picasso.get().setIndicatorsEnabled(false)

                Picasso.get().load(imageUrl).fit().networkPolicy(NetworkPolicy.OFFLINE)
                    .into(binding.pokeImg,
                        object : Callback {
                            override fun onSuccess() {

                            }

                            override fun onError(e: Exception?) {
                                Picasso.get().load(R.drawable.ic_pok__ball_icon).fit()
                                    .into(binding.pokeImg)
                            }

                        })
                binding.pokeName.text = this.name

                if (this.isFavorite) {
                    binding.imageView.setImageDrawable(favorite)
                } else {
                    binding.imageView.setImageDrawable(unfavorite)

                }
            }
        }


    }

    override fun getItemCount() = pokemonsList.size

    fun search(searchList: MutableList<Pokemon>) {
        pokemonsList = searchList
        notifyDataSetChanged()
    }

    interface InteractInterface {
        fun makeFav(pokemon: Pokemon, position: Int)
        fun viewInfo(pokemon: Pokemon, position: Int)
    }
}