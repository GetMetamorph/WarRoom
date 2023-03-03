package com.example.warroom.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.warroom.R
import com.example.warroom.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.configureToolbar()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_profile, menu)
        return true
    }


    private fun configureToolbar() {
        val toolbar: Toolbar = findViewById(R.id.profil_toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setTitle("Profile");
    }



}