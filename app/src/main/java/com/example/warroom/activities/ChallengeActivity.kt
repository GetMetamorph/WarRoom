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
import com.example.warroom.models.Challenge
import com.example.warroom.models.ChallengeEnum

class ChallengeActivity: AlertActivity(), ChallengeAdapter.ChallengeItemInterface {
    private lateinit var binding: ActivityChallengeBinding
    private var challengeAdapter = ChallengeAdapter(this, this)

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
        when(challenge.type) {
            ChallengeEnum.SPORT -> {
                this.showFormAlert(this, resources.getString(challenge.type.getTitleResId())) {

                }
            }
            else -> {
                Toast.makeText(this,R.string.unavailable_challenge, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private class ChallengeMarginItemDecoration(val context: Context, val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val itemPosition = parent.getChildAdapterPosition(view)
            outRect.bottom = context.resources.getDimensionPixelSize(R.dimen.challenges_item_margin)
            outRect.top = context.resources.getDimensionPixelSize(R.dimen.challenges_item_margin)
        }
    }

}