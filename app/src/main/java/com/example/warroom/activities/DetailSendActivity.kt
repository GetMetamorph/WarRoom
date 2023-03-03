package com.example.warroom.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.warroom.R

class DetailSendActivity : AppCompatActivity(){

    companion object {
        private lateinit var request: Request

        fun newIntent(context: Context, request: Request): Intent {
            return Intent(context, DetailSendActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cancel_button)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, DetailSendFragment())
            .commitAllowingStateLoss()
    }
}

class DetailSendFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.detail_request_fragment, container, false)
    }
}