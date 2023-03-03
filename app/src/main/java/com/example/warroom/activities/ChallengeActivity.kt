package com.example.warroom.activities

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.warroom.R
import com.example.warroom.adapters.ChallengeAdapter
import com.example.warroom.databinding.ActivityChallengeBinding
import com.example.warroom.model.Discussion
import com.example.warroom.models.Challenge
import com.example.warroom.models.ChallengeEnum
import com.example.warroom.viewModels.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ChallengeActivity: AppCompatActivity(), ChallengeAdapter.ChallengeItemInterface {
    private lateinit var binding: ActivityChallengeBinding
    private var userViewModel = UserViewModel()
    private var alertDialogView: AlertDialog? = null
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
        setUpRecyclerview()
        setUpUserViewModelObservers()
    }


    private fun setUpUI() {
        //binding.challengeTitle.text = resources.getString(R.string.challenge_title)
        binding.challengeRecyclerview.adapter = challengeAdapter
    }

    private fun setUpRecyclerview() {
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
                this.showFormAlert(this, resources.getString(challenge.type.getTitleResId()))
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

    private fun setUpUserViewModelObservers() {
        userViewModel.userAvailable.observe(this@ChallengeActivity, Observer { userId ->
            userId?.let {
                this.alertDialogView?.dismiss()
                val intent = SportChallengeActivity.newIntent(this, it)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                this.startActivity(intent)
                finish()
            } ?: run {
                Toast.makeText(
                    this,
                    resources.getString(R.string.challenge_form_wrong_pseudo),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun showFormAlert(context: Context, title: String){
        val builder = AlertDialog.Builder(context)
        val alertdialogView = builder.create()
        alertdialogView.setCanceledOnTouchOutside(true)

        val view = LayoutInflater.from(context).inflate(R.layout.alert_challenge_form, null)
        alertdialogView.setView(view)
        this.alertDialogView = alertdialogView
        alertdialogView.window?.setBackgroundDrawableResource(R.color.transparent)

        val formTitle = view.findViewById<TextView>(R.id.form_title)
        val username = view.findViewById<TextView>(R.id.username_label)
        val date = view.findViewById<TextView>(R.id.date_label)
        val reason = view.findViewById<TextView>(R.id.reason_label)
        val description = view.findViewById<TextView>(R.id.description_label)
        val usernameEdit = view.findViewById<TextView>(R.id.username_edit_text)
        val dateEdit = view.findViewById<TextView>(R.id.date_edit_text)
        val reasonEdit = view.findViewById<TextView>(R.id.reason_edit_text)
        val descriptionEdit = view.findViewById<TextView>(R.id.description_edit_text)
        val validationButton = view.findViewById<Button>(R.id.valid_form_button)


        formTitle?.let {
            it.text = title
        }

        username?.let {
            it.text = context.getString(R.string.challenge_form_username)
        }

        date?.let {
            it.text = context.getString(R.string.challenge_form_date)
        }

        reason?.let {
            it.text = context.getString(R.string.challenge_form_reason)
        }

        description?.let {
            it.text = context.getString(R.string.challenge_form_description)
        }

        validationButton?.let {
            it.setOnClickListener {
                Log.d("ChallengeActivityDate","${date.text}")
                this.checkFields(
                    usernameEdit.text.toString(),
                    dateEdit.text.toString(),
                    reasonEdit.text.toString(),
                    descriptionEdit.text.toString()
                )
            }
        }
        alertdialogView.show()
    }

    private fun checkFields(
        username: String,
        date: String,
        reason: String,
        description: String) {

        //Check date
        if (date.isNotBlank()) {
            userViewModel.getUserWith(username)
        } else {
            Toast.makeText(
                this,
                resources.getString(R.string.challenge_form_wrong_date_fomat),
                Toast.LENGTH_SHORT
            ).show()
        }

        //send call

    }

}