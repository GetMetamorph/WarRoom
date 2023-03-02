package com.example.warroom.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.warroom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.setUpUI()
    }

    private fun setUpUI() {
        binding.challengesButton.setOnClickListener {
            val intent = ChallengeActivity.newIntent(this)
            startActivity(intent)
        }
    }
}