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
import com.dadon.pokemon.databinding.SignupFragmentBinding
import com.dadon.pokemon.ui.views.activities.UiActivity
import com.dadon.pokemon.viewmodels.LoginViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignupFragment : Fragment(R.layout.signup_fragment) {
    var binding: SignupFragmentBinding? = null
    var viewModel: LoginViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SignupFragmentBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingViewModel()
        observeLogin()
        signingupClick()

        binding?.login?.setOnClickListener {
            NavHostFragment.findNavController(this@SignupFragment)
                .navigate(R.id.action_signupFragment_to_loginFragment)
        }
    }


    private fun settingViewModel() {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }


    private fun signup() {
        val mail = binding?.mail?.text.toString()
        val password = binding?.password?.text.toString()
        viewModel?.signup(mail, password)
    }

    private fun observeLogin() {
        viewModel?.isSuccess?.observe(viewLifecycleOwner) {
           gotoHome()
        }

    }

    private fun signingupClick() {
        binding?.signupButton?.setOnClickListener {
            println(ismactched())
            if (checkingEmpty()) {
                handlingEmptyTexts()
            } else {
                if (ismactched()) {
                    signup()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "please enter the same password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun ismactched() =
        binding?.password?.text.toString() == binding?.passwordcheck?.text.toString()





    private fun checkingEmpty() =
        binding?.mail?.text.isNullOrEmpty() || binding?.password?.text.isNullOrEmpty() || binding?.password?.text.isNullOrEmpty()

    private fun handlingEmptyTexts() {
        errorEmpty(binding!!.mail, binding!!.mailLayout, "* required")
        errorEmpty(binding!!.password, binding!!.passwordLayout, "* required")
        errorEmpty(binding!!.passwordcheck, binding!!.passwordcheckLayout, "* required")
    }

    fun errorEmpty(textview: TextInputEditText, layout: TextInputLayout, msg: String) {
        if (textview.text!!.isEmpty()) {
            layout.error = msg

        } else {
            layout.isErrorEnabled = false
        }
    }

    private fun gotoHome() {
        val intent = Intent(requireActivity(), UiActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }


}