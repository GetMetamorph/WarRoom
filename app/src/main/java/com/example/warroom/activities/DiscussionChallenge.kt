package com.example.warroom.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.warroom.MyScrollToBottomObserver
import com.example.warroom.activities.adapters.DiscussionAdapter
import com.example.warroom.databinding.ActivityDiscussionChallengeBinding
import com.example.warroom.model.DiscussionMessage
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import net.datafaker.Faker
import java.util.Arrays


class DiscussionChallenge : AppCompatActivity() {
    private lateinit var binding: ActivityDiscussionChallengeBinding

    // Firebase instance variables
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: DiscussionAdapter
    private lateinit var manager: LinearLayoutManager
    private lateinit var discussionId: String
    private lateinit var faker: Faker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras
        discussionId = extras?.getString("discussion_id") ?: ""
        // This codelab uses View Binding
        // See: https://developer.android.com/topic/libraries/view-binding
        binding = ActivityDiscussionChallengeBinding.inflate(layoutInflater)
          faker = Faker()
        setContentView(binding.root)


        // Initialize Firebase Auth and check if the user is signed in
        auth = Firebase.auth
        if (auth.currentUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        // Initialize Realtime Database
        db = FirebaseFirestore.getInstance()
        val query = db.collection("discussion").whereEqualTo("documentId", discussionId)

        // The FirebaseRecyclerAdapter class and options come from the FirebaseUI library
        // See: https://github.com/firebase/FirebaseUI-Android
        val options = FirestoreRecyclerOptions.Builder<DiscussionMessage>()
            .setQuery(query, DiscussionMessage::class.java)
            .setLifecycleOwner(this)
            .build()
        adapter = DiscussionAdapter(options)
        manager = LinearLayoutManager(this)
        manager.stackFromEnd = true
        binding.recyclerview.layoutManager = manager


        // Scroll down when a new message arrives
        // See MyScrollToBottomObserver for details
        adapter.registerAdapterDataObserver(
            MyScrollToBottomObserver(binding.recyclerview, adapter, manager)
        )
        binding.sendButton.setOnClickListener {
            val message = DiscussionMessage(
                getUserName(),
                getPhotoUrl(),
                faker.avatar().image()
            )
            val addMesssageToMessages: MutableMap<String, Any> = HashMap()
            addMesssageToMessages["messages"] =
                FieldValue.arrayUnion(message)

            val messagesRef = db.collection("discussions")
                .document(discussionId)
            messagesRef.update(addMesssageToMessages)

            //binding.messageEditText.setText("")
        }
        binding.recyclerview.adapter = adapter
        // Disable the send button when there's no text in the input field
        // See MyButtonObserver for details
        // binding.messageEditText.addTextChangedListener(MyButtonObserver(binding.sendButton))

        // When the send button is clicked, send a text message


    }



    public override fun onStart() {
        super.onStart()
        // Check if user is signed in.
        if (auth.currentUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
    }

    public override fun onPause() {
        adapter.stopListening()
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
        adapter.startListening()
    }

    private fun getPhotoUrl(): String? {
        val user = auth.currentUser
        return user?.photoUrl?.toString()
    }

    private fun getUserName(): String? {
        val user = auth.currentUser
        return if (user != null) {
            user.email
        } else DiscussionAdapter.ANONYMOUS

    }
}
