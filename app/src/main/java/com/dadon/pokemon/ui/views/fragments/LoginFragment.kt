package com.dadon.pokemon.ui.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.dadon.pokemon.R
import com.dadon.pokemon.databinding.LoginFragmentBinding
import com.dadon.pokemon.viewmodels.LoginViewModel
import com.dadon.pokemon.ui.views.activities.UiActivity

class LoginFragment : Fragment(R.layout.login_fragment) {

    var binding: LoginFragmentBinding? = null
    var viewModel: LoginViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        goToSignup()
        settingViewModel()
        observeLogin()
        signingupClick()


    }


    private fun settingViewModel() {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }


    private fun signin() {
        val mail = binding?.mailSignin?.text.toString()
        val password = binding?.passSigin?.text.toString()
        viewModel?.signIn(mail, password)
    }

    private fun observeLogin() {
        viewModel?.isSuccess?.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
            gotoHome()
        }

    }

    private fun signingupClick() {
        binding?.loginBtu?.setOnClickListener {
            if (isEmpty()) {
                Toast.makeText(requireContext(), "please fill your info", Toast.LENGTH_SHORT).show()
            } else {
                signin()

            }

        }
    }


    private fun goToSignup() {
        binding?.signup?.setOnClickListener {
            NavHostFragment.findNavController(this@LoginFragment)
                .navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }


    private fun gotoHome() {
        val intent = Intent(requireActivity(), UiActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun isEmpty() =
        binding?.mailSignin?.text.isNullOrEmpty() || binding?.passSigin?.text.isNullOrEmpty()


}