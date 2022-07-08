package com.dadon.pokemon.ui.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.dadon.pokemon.R
import com.dadon.pokemon.databinding.InfoviewpagerFragmentBinding
import com.dadon.pokemon.ui.adapters.InfoPageViewerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.smarteist.autoimageslider.SliderView

class InoViewPager : Fragment(R.layout.infoviewpager_fragment) {
    var binding: InfoviewpagerFragmentBinding? = null
    lateinit var pager: ViewPager2
    lateinit var pagerAdapter: InfoPageViewerAdapter
    lateinit var tab: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        pagerSetup()
    }

    fun pagerSetup() {
        tab = binding?.tabs!!
        pager = binding?.homePager!!
        pagerAdapter =
            InfoPageViewerAdapter(
                requireActivity().supportFragmentManager,
                requireActivity().lifecycle
            )
        pager.adapter = pagerAdapter
        TabLayoutMediator(tab, pager) {

                tab, position ->
            val tabtitles = mutableListOf<String>("about", "abilities")
            tab.text = tabtitles[position]

        }.attach()
    }
}