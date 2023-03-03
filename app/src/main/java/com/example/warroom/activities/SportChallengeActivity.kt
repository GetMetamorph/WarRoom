package com.example.warroom.activities

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.SpannableString
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.warroom.R
import com.example.warroom.databinding.ActivitySportChallengeBinding
import com.example.warroom.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import net.datafaker.Faker
import java.util.*


class SportChallengeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySportChallengeBinding
    private var countDownTimer: CountDownTimer? = null
    private var timeLeft: Long = 0
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    private lateinit var mediaPlayerUp : MediaPlayer
    private lateinit var mediaPlayerDown : MediaPlayer
    private lateinit var mediaPlayer : MediaPlayer
    private var challengeStarted = false
    private var playerIsUp = true
    private var playerScore = 0


    companion object {
        private var opponentUserId: String? = null
        fun newIntent(context: Context, userId: String? = null): Intent {
            opponentUserId = userId
            return Intent(context, SportChallengeActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySportChallengeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        binding.sportChallengeDesc.text = getString(R.string.sport_challenge_desc)
        val str = getString(R.string.sport_challenge_desc)

        this.mediaPlayerUp = MediaPlayer.create(this, R.raw.flexup)
        this.mediaPlayerDown = MediaPlayer.create(this, R.raw.flexdown)
        this.mediaPlayer = MediaPlayer.create(this, R.raw.eye_)
        mediaPlayer.start()


        val spannableString = SpannableString(str)

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

                        showEndOfChallengeSenderPopUp()

                        val name = "Sport Challenge"
                        val receiver_id = opponentUserId
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

        /*val animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1000
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener { animation ->
                for (i in 0 until 5) {
                    val offset = Math.sin(animation.animatedValue as Float * Math.PI + i * Math.PI / 2).toFloat() * 10
                    binding.sportChallengeDesc.text = spannableString.apply {
                        setSpan(
                            VerticalOffsetSpan(offset),
                            str.indexOf("SQUAT"[i]),
                            str.indexOf("SQUAT"[i]) + 1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                }
            }
        }
        animator.start()*/
    }

    override fun onDestroy() {
        super.onDestroy()
        this.mediaPlayer.stop()
        this.mediaPlayerUp.stop()
        this.mediaPlayerDown.stop()
    }

    private fun showEndOfChallengeSenderPopUp(){
        val builder = AlertDialog.Builder(this)
        val alertdialogView = builder.create()
        alertdialogView.setCancelable(false)
        alertdialogView.setCanceledOnTouchOutside(false)

        val view = LayoutInflater.from(this).inflate(R.layout.alert_sport_challenge_sender, null)
        alertdialogView.setView(view)
        alertdialogView.window?.setBackgroundDrawableResource(R.color.transparent)

        val alertTitle = view.findViewById<TextView>(R.id.alert_sport_challenge_result)
        val alertMessage = view.findViewById<TextView>(R.id.alert_sport_challenge_message)
        val alertImage = view.findViewById<ImageView>(R.id.alert_sport_challenge_image)
        val alertValidButton = view.findViewById<Button>(R.id.alert_sport_challenge_valid_button)

        alertValidButton?.let {
            it.text = resources.getString(R.string.sport_challenge_form_validate_button)
            it.setOnClickListener {
                alertdialogView.dismiss()
                this.finish()
            }
        }

        alertTitle?.let {
            it.text = "Score : $playerScore"
        }

        alertMessage?.let {
            it.text = resources.getString(R.string.sport_challenge_form_message)
        }

        alertImage?.let {
            Glide.with(this).load("https://media.giphy.com/media/tXL4FHPSnVJ0A/giphy.gif?cid=ecf05e47g7lkn0lqavduez7t1r6uxr160bz8441a6vf6f5b4&rid=giphy.gif&ct=g").into(it);
        }

        alertdialogView.show()
    }

    private fun showEndOfChallengeReceiverPopUp(){
        val builder = AlertDialog.Builder(this)
        val alertdialogView = builder.create()
        alertdialogView.setCancelable(false)
        alertdialogView.setCanceledOnTouchOutside(false)

        val view = LayoutInflater.from(this).inflate(R.layout.alert_sport_challenge_receiver, null)
        alertdialogView.setView(view)
        alertdialogView.window?.setBackgroundDrawableResource(R.color.transparent)

        val alertTitle = view.findViewById<TextView>(R.id.alert_sport_challenge_title)
        val alertReceiverResult = view.findViewById<TextView>(R.id.alert_sport_challenge_receiver_result)
        val alertSenderResult = view.findViewById<TextView>(R.id.alert_sport_challenge_sender_result)
        val alertImage = view.findViewById<ImageView>(R.id.alert_sport_challenge_image)
        val alertValidButton = view.findViewById<Button>(R.id.alert_sport_challenge_valid_button)

        alertValidButton?.let {
            it.text = resources.getString(R.string.sport_challenge_form_validate_button)
            it.setOnClickListener {
                alertdialogView.dismiss()
                this.finish()
            }
        }

        alertTitle?.let {
            it.text = resources.getString(R.string.sport_challenge_receiver_win)
        }

        alertReceiverResult?.let {
            it.text = "Ton score : $playerScore"
        }

        alertSenderResult?.let {
            it.text = "Score adverse : "
        }

        alertdialogView.show()
    }
}

/*class VerticalOffsetSpan(private val offsetY: Float) : ReplacementSpan() {

    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        return paint.measureText(text, start, end).toInt()
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        canvas.drawText(text!!, start, end, x, y + offsetY, paint)
    }
}*/