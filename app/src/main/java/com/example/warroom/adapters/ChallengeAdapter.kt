package com.example.warroom.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.warroom.R
import com.example.warroom.databinding.ItemChallengeBinding
import com.example.warroom.models.Challenge

class ChallengeAdapter(val context: Context, val listener: ChallengeItemInterface): Adapter<RecyclerView.ViewHolder>() {
    private var challenges: List<Challenge>? = null
    private var attachedRecyclerViewHeight: Int? = null

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

        attachedRecyclerViewHeight?.let { recyclerViewHeight ->
            challenges?.let { c ->
                viewHolder.itemBinding.itemChallengeMainLayout.layoutParams.height = recyclerViewHeight / c.size
            }
        }


        challenge?.let {
            viewHolder.itemBinding.itemChallengeMainLayout.background = ResourcesCompat.getDrawable(context.resources, it.type.getColorDrawableResId(), null)
            viewHolder.itemBinding.challengeTitle.text = context.getString(challenge.type.getTitleResId())
            viewHolder.itemBinding.itemChallengeButton.setOnClickListener {
                this.listener.onClick(challenge)
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setUpChallenges(challenges: List<Challenge>?) {
        this.challenges = challenges
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAttachedRecyclerViewHeight(height: Int) {
        this.attachedRecyclerViewHeight = height
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return challenges?.size ?: 0
    }

    class ChallengeItemViewHolder(val itemBinding: ItemChallengeBinding): RecyclerView.ViewHolder(itemBinding.root)
}