package com.dadon.pokemon.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dadon.pokemon.ui.views.fragments.AbilityFragment
import com.dadon.pokemon.ui.views.fragments.AboutFragment


class InfoPageViewerAdapter(fragmentManger: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManger, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {

            0 -> AboutFragment()
            else -> AbilityFragment()
        }
    }

}