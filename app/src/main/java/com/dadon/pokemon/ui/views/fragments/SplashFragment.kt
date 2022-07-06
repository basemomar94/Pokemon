package com.dadon.pokemon.ui.views.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.dadon.pokemon.R
import com.dadon.pokemon.ui.views.activities.UiActivity
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment(R.layout.splash_fragment) {

    override fun onStart() {
        super.onStart()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed(Runnable {
            checkLoginStatus()
        }, 1000)


    }


    private fun checkLoginStatus() {
        val auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            gotoHome()

        } else {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_splashFragment_to_loginFragment)

        }
    }


    private fun gotoHome() {
        val intent = Intent(requireActivity(), UiActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}