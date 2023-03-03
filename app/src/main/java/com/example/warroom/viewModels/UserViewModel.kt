package com.example.warroom.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warroom.models.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private val db = Firebase.firestore

    private var _userAvailable: MutableLiveData<User?> = MutableLiveData()
    val userAvailable : LiveData<User?> = _userAvailable


    fun getUserWith(username: String) {
        viewModelScope.launch {
            try {
                db.collection("users")
                    .whereEqualTo("username", username)
                    .get()
                    .addOnSuccessListener { documents ->
                        val doc = documents.first()
                        Log.d("UserDoc","$documents")
                        _userAvailable.value = doc.toObject(User::class.java)
                        Log.d("UserDoc","$userAvailable")
                    }
                    .addOnFailureListener { exception ->
                        Log.d("UserViewModelError", "Error getting documents: ", exception)
                        _userAvailable.value = null
                    }
            }catch (e: Exception){
                Log.d("UserViewModelError", "${e.message}")
                _userAvailable.value = null
            }
        }
    }

}