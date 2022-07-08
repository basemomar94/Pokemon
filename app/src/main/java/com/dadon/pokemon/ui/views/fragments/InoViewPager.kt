package com.dadon.pokemon.ui.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.dadon.pokemon.R
import com.dadon.pokemon.databinding.InfoviewpagerFragmentBinding
import com.dadon.pokemon.models.Pokemon
import com.dadon.pokemon.ui.adapters.InfoPageViewerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.smarteist.autoimageslider.SliderView

class InoViewPager : Fragment(R.layout.infoviewpager_fragment) {
    var binding: InfoviewpagerFragmentBinding? = null
    var tabtitle = arrayOf("About", "Abilities")
    private lateinit var pager2: ViewPager2
    private lateinit var tabl: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("here we are")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = InfoviewpagerFragmentBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pager2 = view.findViewById(R.id.homePager)
        tabl = view.findViewById(R.id.tabs)
        pager2.adapter = InfoPageViewerAdapter(
            requireActivity().supportFragmentManager,
            requireActivity().lifecycle
        )
        TabLayoutMediator(tabl, pager2) {

                tab, position ->
            tab.text = tabtitle[position]

        }.attach()
        updateUi()


    }


    private fun updateUi() {
        val bundle = this.arguments
//        val pokemon = bundle?.getSerializable("pokemon") as Pokemon
      //  println(pokemon.name)

    }
}