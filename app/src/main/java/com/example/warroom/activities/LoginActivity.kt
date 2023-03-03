package com.example.warroom.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.warroom.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        print("loginActivity started")

        binding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.forgotPasswordText.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.mailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            print("loginButton clicked")

            if (email.isEmpty() || password.isEmpty()) {
                binding.alertMessage.text = "Veuillez remplir tous les champs"
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,"Connexion réussie", Toast.LENGTH_SHORT).show();
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                    
                        val intent = Intent(this, HomeActivity::class.java)
                        
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        binding.alertMessage.text = "Connexion échouée"
                    }
                }
        }
    }
}