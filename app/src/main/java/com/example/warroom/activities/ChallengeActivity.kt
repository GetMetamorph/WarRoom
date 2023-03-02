package com.example.warroom.activities

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.warroom.R
import com.example.warroom.adapters.ChallengeAdapter
import com.example.warroom.databinding.ActivityChallengeBinding
import com.example.warroom.model.Discussion
import com.example.warroom.models.Challenge
import com.example.warroom.models.ChallengeEnum
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ChallengeActivity : AlertActivity(), ChallengeAdapter.ChallengeItemInterface {
    private lateinit var binding: ActivityChallengeBinding
    private var challengeAdapter = ChallengeAdapter(this, this)
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var testList: List<Challenge> = listOf(
        Challenge(ChallengeEnum.INTELLECTUAL),
        Challenge(ChallengeEnum.SPORT),
        Challenge(ChallengeEnum.TALK)
    )

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ChallengeActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChallengeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = FirebaseFirestore.getInstance()
        auth = Firebase.auth
        setUpUI()
        setUpViewModel()
    }


    private fun setUpUI() {
        //binding.challengeTitle.text = resources.getString(R.string.challenge_title)
        binding.challengeRecyclerview.adapter = challengeAdapter
    }

    private fun setUpViewModel() {
        //binding.challengeRecyclerview.addItemDecoration(ChallengeMarginItemDecoration(this, challengeAdapter))
        challengeAdapter.setUpChallenges(testList)
        val h = resources.displayMetrics.heightPixels  //binding.challengeHeader.height
        Log.d("ChallengeActivity", "recyclerview height: $h")
        challengeAdapter.setAttachedRecyclerViewHeight(h)
    }

    override fun onClick(challenge: Challenge) {
        Log.d("ChallengeItem", "Challenge clicked: ${challenge.type}")
        when (challenge.type) {
            ChallengeEnum.SPORT -> {
                this.showFormAlert(this, resources.getString(challenge.type.getTitleResId())) {

                }
            }
            ChallengeEnum.TALK -> {
                val intent = Intent(this, DiscussionChallenge::class.java)
                db.collection("discussions").add(Discussion()).addOnCompleteListener {
                    if (it.isSuccessful) {
                        it.result?.let { discussion ->
                            intent.putExtra("discussion_id", discussion.id)
                            db.collection("challenges")
                                .add(
                                    com.example.warroom.model.DiscussionChallenge(
                                        "Discusion",
                                        auth.currentUser?.uid.toString(),
                                        "",
                                        discussion.id
                                    )
                                ).addOnCompleteListener {
                                    startActivity(intent)
                                }

                        }
                    }
                }

            }
            else -> {
                Toast.makeText(this, R.string.unavailable_challenge, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private class ChallengeMarginItemDecoration(
        val context: Context,
        val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    ) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val itemPosition = parent.getChildAdapterPosition(view)
            outRect.bottom = context.resources.getDimensionPixelSize(R.dimen.challenges_item_margin)
            outRect.top = context.resources.getDimensionPixelSize(R.dimen.challenges_item_margin)
        }
    }

}