package com.dadon.pokemon.ui.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dadon.pokemon.R
import com.dadon.pokemon.databinding.AbilityFragmentBinding
import com.dadon.pokemon.models.Abilities
import com.dadon.pokemon.viewmodels.PokemonViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AbilityFragment : Fragment(R.layout.ability_fragment) {

    var binding: AbilityFragmentBinding? = null
    var viewModel: PokemonViewModel? = null
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AbilityFragmentBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intitViewModel()
        getAbilites()
        observeAbilities()
    }

    private fun intitViewModel() {
        viewModel = ViewModelProvider(this)[PokemonViewModel::class.java]

    }


    private fun getAbilites() {
        val id = (1..99).random().toString()
        uiScope.launch {
            viewModel?.getPokemonAbilites(id)

        }
    }

    private fun observeAbilities() {
        viewModel?.pokemonAbilities?.observe(viewLifecycleOwner) {
            println(it)
            if (it != null) {
                updateUi(it)
            }

        }
    }

    private fun updateUi(abilities: Abilities) {
        val weight = abilities.weight
        binding?.weightPro?.max = 400
        binding?.weightPro?.progress = weight

        val heightvalue = abilities.height
        binding?.apply {
            heightPro.apply {
                max = 50
                progress = heightvalue
            }
        }

        val baseExperience = abilities.base_experience
        binding?.apply {
            baseExPro.apply {
                max = 300
                progress = baseExperience
            }
        }

        val baseEffort = abilities.stats[0].base_stat
        binding?.baseeffortPro?.apply {
            max = 300
            progress = baseEffort
        }

    }
}