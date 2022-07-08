package com.dadon.pokemon.ui.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dadon.pokemon.R
import com.dadon.pokemon.databinding.AboutFragmentBinding
import com.dadon.pokemon.models.Description
import com.dadon.pokemon.utilits.CONSTANTS.ID
import com.dadon.pokemon.viewmodels.PokemonViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class AboutFragment : Fragment(R.layout.about_fragment) {

    var binding: AboutFragmentBinding? = null
    private var viewModel: PokemonViewModel? = null
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
        binding = AboutFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("inside")
        initViewModel()
        getAboutPokemon()
        observeDetails()
    }


    private fun initViewModel() {
        viewModel = ViewModelProvider(this@AboutFragment)[PokemonViewModel::class.java]

    }

    private fun getAboutPokemon() {
        var bundle = this.arguments
        println(bundle?.getString("5"))

        val id = (1..100).random()

        uiScope.launch {
            viewModel?.getPokemonInfo(id.toString())
        }


    }

    private fun observeDetails() {
        viewModel?.pokemonDesc?.observe(viewLifecycleOwner) {
            println(it.form_descriptions)
            updateUi(it)
        }
    }

    private fun updateUi(description: Description) {

        binding?.textView4?.text = description.flavor_text_entries[0].flavor_text
        binding?.genera?.text = description.genera.get(0).genus
        binding?.generation?.text = description.generation.name

    }
}