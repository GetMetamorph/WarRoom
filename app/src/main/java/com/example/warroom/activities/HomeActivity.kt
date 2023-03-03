package com.example.warroom.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warroom.R
import com.example.warroom.activities.adapters.RequestAdapter
import com.example.warroom.databinding.ActivityHomeBinding
import com.example.warroom.viewModels.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var userViewModel = UserViewModel()
    private lateinit var auth: FirebaseAuth
    private var challengesReceived: List<Request>? = null
    private var challengesAwaiting: List<Request>? = null
    private lateinit var adapterAwaiting: RequestAdapter
    private lateinit var adapterReceived: RequestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        this.configureToolbar()
        this.setUpUI()
        this.setUpViewModelObserver()
        this.initRecyclerViews()

        auth.currentUser?.let {
            userViewModel.getUserChallengesAwaiting(it.uid)
            userViewModel.getUserChallengesReceived(it.uid)
        }

    }

    public override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_home, menu)
        return true
    }

    private fun configureToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun setUpUI() {
        binding.iconEmpty.setOnClickListener {
            val intent = ChallengeActivity.newIntent(this)
            startActivity(intent)
        }
        binding.challengesAvailableView.visibility = View.GONE
    }

    private fun setUpViewModelObserver() {
        userViewModel.userChallengesReceived.observe(this@HomeActivity, Observer { requests ->
            requests?.let {
                challengesReceived = it
                adapterReceived.setAdapterItems(it)
                if(it.isNotEmpty()) {
                    binding.challengesAvailableView.visibility = View.VISIBLE
                    binding.noChallengesView.visibility = View.GONE
                }
            } ?: run {
                adapterReceived.setAdapterItems(null)
            }
        })

        userViewModel.userChallengesAwaiting.observe(this@HomeActivity, Observer { requests ->
            requests?.let {
                challengesAwaiting = it
                adapterAwaiting.setAdapterItems(it)
                if(it.isNotEmpty()) {
                    binding.challengesAvailableView.visibility = View.VISIBLE
                    binding.noChallengesView.visibility = View.GONE
                }
            } ?: run {
                adapterAwaiting.setAdapterItems(null)
            }
        })
    }

    private fun initRecyclerViews() {
        val horizontalLayoutManagerReceive = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapterReceived = RequestAdapter(this)
        binding.receiveRequestRecyclerView.adapter = adapterReceived
        binding.receiveRequestRecyclerView.layoutManager = horizontalLayoutManagerReceive

        val horizontalLayoutManagerWaiting = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapterAwaiting = RequestAdapter(this)
        binding.waitingRequestRecyclerView.adapter = adapterAwaiting
        binding.waitingRequestRecyclerView.layoutManager = horizontalLayoutManagerWaiting
    }
    
}