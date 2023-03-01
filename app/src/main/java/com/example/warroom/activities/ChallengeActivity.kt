package com.example.warroom.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.warroom.adapters.ChallengeAdapter
import com.example.warroom.databinding.ActivityChallengeBinding
import com.example.warroom.models.Challenge

class ChallengeActivity: AppCompatActivity(), ChallengeAdapter.ChallengeItemInterface {
    private lateinit var binding: ActivityChallengeBinding
    private var challengeAdapter = ChallengeAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpUI()
    }

    private fun setUpUI() {
        binding.challengeRecyclerview.adapter = challengeAdapter
    }

    override fun onClick(challenge: Challenge) {
        //TODO("Not yet implemented")
    }

}