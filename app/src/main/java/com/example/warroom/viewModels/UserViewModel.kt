package com.example.warroom.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private val db = Firebase.firestore

    private var _userAvailable: MutableLiveData<Boolean> = MutableLiveData()
    val userAvailable : LiveData<Boolean> = _userAvailable


    fun getUserWith(username: String) {
        viewModelScope.launch {
            try {
                db.collection("users")
                    .whereEqualTo("username", username)
                    .get()
                    .addOnSuccessListener { documents ->
                        _userAvailable.value = !documents.isEmpty
                    }
                    .addOnFailureListener { exception ->
                        Log.d("UserViewModelError", "Error getting documents: ", exception)
                        _userAvailable.value = false
                    }
            }catch (e: Exception){
                Log.d("UserViewModelError", "${e.message}")
                _userAvailable.value = false
            }
        }
    }

}