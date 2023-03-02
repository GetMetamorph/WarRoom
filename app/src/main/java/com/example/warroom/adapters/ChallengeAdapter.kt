package com.example.warroom.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.warroom.databinding.ItemChallengeBinding
import com.example.warroom.models.Challenge

class ChallengeAdapter(val context: Context, val listener: ChallengeItemInterface): Adapter<RecyclerView.ViewHolder>() {
    private var challenges: List<Challenge>? = null

    interface ChallengeItemInterface {
        fun onClick(challenge: Challenge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = ItemChallengeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChallengeItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: ChallengeItemViewHolder = holder as ChallengeItemViewHolder
        val challenge = challenges?.get(viewHolder.adapterPosition)

        challenge?.let {
            viewHolder.itemBinding.challengeTitle.text = context.getString(challenge.type.getTitleResId())
            viewHolder.itemBinding.itemChallengeMainLayout.setOnClickListener {
                this.listener.onClick(challenge)
            }
        }

    }

    fun setUpChallenges(challenges: List<Challenge>?) {
        this.challenges = challenges
    }

    override fun getItemCount(): Int {
        return challenges?.size ?: 0
    }

    class ChallengeItemViewHolder(val itemBinding: ItemChallengeBinding): RecyclerView.ViewHolder(itemBinding.root)
}