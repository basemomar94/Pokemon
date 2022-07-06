package com.dadon.pokemon.repos

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class LoginRepo {

    val auth: FirebaseAuth = FirebaseAuth.getInstance()



    fun signup(email: String, password: String, isSuccess: MutableLiveData<Boolean>) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                isSuccess.postValue(true)
            }

        }.addOnFailureListener {
            isSuccess.postValue(false)
           println(it.message)
        }

    }

    fun signIn(email: String, password: String, isSuccess: MutableLiveData<Boolean>) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                isSuccess.postValue(true)
            }

        }.addOnFailureListener {
            isSuccess.postValue(false)
            println(it.message)
        }

    }

}