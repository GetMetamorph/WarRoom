package com.example.warroom.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warroom.activities.Request
import com.example.warroom.models.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private val db = Firebase.firestore

    private var _userAvailable: MutableLiveData<String?> = MutableLiveData()
    val userAvailable : LiveData<String?> = _userAvailable

    private var _userChallengesAwaiting: MutableLiveData<List<Request>?> = MutableLiveData()
    val userChallengesAwaiting : LiveData<List<Request>?> = _userChallengesAwaiting

    private var _userChallengesReceived: MutableLiveData<List<Request>?> = MutableLiveData()
    val userChallengesReceived : LiveData<List<Request>?> = _userChallengesReceived


    fun getUserWith(username: String) {
        viewModelScope.launch {
            try {
                db.collection("users")
                    .whereEqualTo("username", username)
                    .get()
                    .addOnSuccessListener { documents ->
                        val doc = documents.first()
                        Log.d("UserDoc","$documents")

                        _userAvailable.value = doc.id//doc.toObject(User::class.java)
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

    fun getUserChallengesAwaiting(userId: String) {
        viewModelScope.launch {
            try {
                db.collection("challenges")
                    .whereEqualTo("sender_id", userId)
                    .whereEqualTo("completed", false)
                    .get()
                    .addOnSuccessListener { documents ->
                        _userChallengesAwaiting.value = documents.toObjects(Request::class.java)
                        Log.d("UserDoc","$userAvailable")
                    }
                    .addOnFailureListener { exception ->
                        Log.d("UserViewModelError", "Error getting documents: ", exception)
                        _userChallengesAwaiting.value = null
                    }
            }catch (e: Exception){
                Log.d("UserViewModelError", "${e.message}")
                _userChallengesAwaiting.value = null
            }
        }
    }

    fun getUserChallengesReceived(userId: String) {
        Log.d("UserId","$userId")
        viewModelScope.launch {
            try {
                db.collection("challenges")
                    .whereEqualTo("receiver_id", userId)
                    .whereEqualTo("completed", false)
                    .get()
                    .addOnSuccessListener { documents ->

                        _userChallengesReceived.value = documents.toObjects(Request::class.java)
                        Log.d("UserDoc","$userAvailable")
                    }
                    .addOnFailureListener { exception ->
                        Log.d("UserViewModelError", "Error getting documents: ", exception)
                        _userChallengesReceived.value = null
                    }
            }catch (e: Exception){
                Log.d("UserViewModelError", "${e.message}")
                _userChallengesReceived.value = null
            }
        }
    }

}