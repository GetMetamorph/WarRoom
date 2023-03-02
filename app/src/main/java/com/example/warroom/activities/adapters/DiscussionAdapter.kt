package com.example.warroom.activities.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.warroom.R
import com.example.warroom.databinding.ImageMessageBinding
import com.example.warroom.model.DiscussionMessage
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class DiscussionAdapter(
    private val options: FirestoreRecyclerOptions<DiscussionMessage>
) : FirestoreRecyclerAdapter<DiscussionMessage, RecyclerView.ViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.image_message, parent, false)
        val binding = ImageMessageBinding.bind(view)
        return ImageMessageViewHolder(binding)

    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        model: DiscussionMessage
    ) {
        (holder as ImageMessageViewHolder).bind(model)
    }

    override fun getItemViewType(position: Int): Int {
        return 2;
    }


    inner class ImageMessageViewHolder(private val binding: ImageMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DiscussionMessage) {
            loadImageIntoView(binding.messageImageView, item.gifUrl!!, false)

            binding.messengerTextView.text = item.name ?: ANONYMOUS
            if (item.photoUrl != null) {
                loadImageIntoView(binding.messengerImageView, item.photoUrl)
            } else {
                binding.messengerImageView.setImageResource(R.drawable.baseline_account_circle_48)
            }
        }
    }

    private fun loadImageIntoView(view: ImageView, url: String, isCircular: Boolean = true) {
        loadWithGlide(view, url, isCircular)
    }

    private fun loadWithGlide(view: ImageView, url: String, isCircular: Boolean = true) {
        Glide.with(view.context).load(url).into(view)
        var requestBuilder = Glide.with(view.context).load(url)
        if (isCircular) {
            requestBuilder = requestBuilder.transform(CircleCrop())
        }
        requestBuilder.into(view)
    }

    companion object {
        const val TAG = "MessageAdapter"
        const val ANONYMOUS = "anonymous"
    }
}