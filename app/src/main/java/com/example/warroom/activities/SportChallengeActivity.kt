package com.example.warroom.activities

import android.content.ContentValues
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableString
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.warroom.R
import com.example.warroom.databinding.ActivitySportChallengeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import net.datafaker.Faker


class SportChallengeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySportChallengeBinding
    private var countDownTimer: CountDownTimer? = null
    private var timeLeft: Long = 0
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySportChallengeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        binding.sportChallengeDesc.text = getString(R.string.sport_challenge_desc)
        val str = getString(R.string.sport_challenge_desc)

        val mediaPlayerUp = MediaPlayer.create(this, R.raw.flexup)
        val mediaPlayerDown = MediaPlayer.create(this, R.raw.flexdown)
        val mediaPlayer = MediaPlayer.create(this, R.raw.eye_)
        mediaPlayer.start()


        val spannableString = SpannableString(str)
        var challengeStarted = false
        var playerIsUp = true
        var playerScore = 0



        binding.sportChallengeButton.setOnClickListener {
            if (!challengeStarted){
                challengeStarted = true
                countDownTimer = object : CountDownTimer(15000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        timeLeft = millisUntilFinished
                        binding.sportChallengeDesc.text = "TIMER : ${timeLeft / 1000}"
                    }

                    override fun onFinish() {
                        binding.sportChallengeDesc.text = "TIMER : 0"
                        binding.sportChallengeButton.visibility = android.view.View.GONE

                        val name = "Sport Challenge"
                        val receiver_id = null
                        val receiver_score = null
                        val sender_id = auth.currentUser?.uid
                        val sender_score = playerScore

                        // Create a new user with a first and last name
                        val challenge = hashMapOf(
                            "name" to name,
                            "receiver_id" to receiver_id,
                            "receiver_score" to receiver_score,
                            "sender_id" to sender_id,
                            "sender_score" to sender_score,
                            "completed" to false
                        )

                        db.collection("challenges")
                            .add(challenge)
                            .addOnSuccessListener { documentReference ->
                                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                            }
                            .addOnFailureListener { e ->
                                Log.w(ContentValues.TAG, "Error adding document", e)
                            }
                    }
                }.start()
                binding.sportChallengeButton.text=""
                binding.sportChallengeScore.visibility = android.view.View.VISIBLE
            }
            if (playerIsUp) {
                mediaPlayerDown.start()
                mediaPlayer.start()
                binding.playerSprite.setImageResource(R.drawable.flexdownspritetransparent)
            } else {
                mediaPlayerUp.start()
                mediaPlayer.start()
                binding.playerSprite.setImageResource(R.drawable.flexupspritetransparent)
                playerScore++
                binding.sportChallengeScore.text = "SCORE : $playerScore"
            }
            playerIsUp = !playerIsUp
            Log.d("Click",  "$playerScore")
        }
    }
}