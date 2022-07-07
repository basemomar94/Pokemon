package com.dadon.pokemon.ui.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dadon.pokemon.R
import com.dadon.pokemon.databinding.AccountFragmentBinding
import com.dadon.pokemon.ui.views.activities.MainActivity
import com.dadon.pokemon.viewmodels.LoginViewModel

class AccountFragment : Fragment(R.layout.account_fragment) {

    var binding: AccountFragmentBinding? = null
    var viewModel: LoginViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AccountFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingViewModel()

        binding?.logout?.setOnClickListener {
         logout()
        }
    }

    private fun settingViewModel() {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }

    private fun logout() {
        viewModel?.logout()
        val intent = Intent(requireActivity(), MainActivity::class.java)
        requireActivity().startActivity(intent)
        requireActivity().finish()
    }
}