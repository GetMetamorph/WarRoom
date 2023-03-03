package com.example.warroom.activities

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.warroom.R
import com.example.warroom.databinding.ActivityRegisterBinding
import net.datafaker.Faker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dk.brics.automaton.SpecialOperations.trim

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private var faker: Faker = Faker()
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.registerButton.setOnClickListener {
            val username = binding.usernameInput.text.toString().trim()
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()
            val confirmPassword = binding.verifPasswordInput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                binding.alertMessage.text = "Veuillez remplir tous les champs"
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                binding.alertMessage.text = "Les mots de passe ne correspondent pas"
                val id = resources.getIdentifier(R.drawable.warning.toString(), null, null)
                binding.alertMessage.text = "Les mots de passe ne correspondent pas"
                binding.iconClose.setImageDrawable(resources.getDrawable(id, null))
                binding.passwordInput.background = resources.getDrawable(R.drawable.button_password_err_bg, null)
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = auth.currentUser
                        if (firebaseUser != null) {
                            // Get the UID of the newly created user
                            val uid = firebaseUser.uid

                            // Create a new user document in Firestore with the UID and other fields
                            val user = hashMapOf(
                                "uid" to uid,
                                "username" to username,
                                "email" to email,
                                "picture" to faker.avatar().image(),
                            )

                        // Sign in success, update UI with the signed-in user's information
                        //val user = auth.currentUser
                        // Add a new document with a generated ID
                        db.collection("users")
                            .add(user)
                            .addOnSuccessListener { documentReference ->
                                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        binding.alertMessage.text = "Inscription échouée"
                    }
                }
        }
    }
}