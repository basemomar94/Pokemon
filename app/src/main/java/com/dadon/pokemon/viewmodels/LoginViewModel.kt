package com.dadon.pokemon.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dadon.pokemon.repos.LoginRepo

class LoginViewModel : ViewModel() {

    val repo = LoginRepo()
    val isSuccess = MutableLiveData<Boolean>()


    fun signup(email: String, password: String) {
        repo.signup(email, password, isSuccess)
    }


    fun signIn(email: String, password: String) {
        repo.signIn(email, password, isSuccess)
    }

    fun logout() {
        repo.logOut()
    }
}