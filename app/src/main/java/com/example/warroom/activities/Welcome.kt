package com.example.warroom.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warroom.R
import com.example.warroom.activities.adapters.RequestAcceptedAdapter
import com.example.warroom.activities.adapters.RequestReceiveAdapter
import com.example.warroom.activities.adapters.RequestWaitingAdapter


class Welcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)
        val horizontalLayoutManagerAccepted = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val list_request_accepted = findViewById<View>(R.id.acceptedRequest) as RecyclerView

        var requests_accepted = Request.createRequest()

        val adapter_accepted = RequestAcceptedAdapter(requests_accepted)
        list_request_accepted.adapter = adapter_accepted
        list_request_accepted.layoutManager = horizontalLayoutManagerAccepted

        // ---------------------//
        val horizontalLayoutManagerReceive = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val list_game_receive = findViewById<View>(R.id.receiveRequest) as RecyclerView

        var requests_receive = Request.createRequest()

        val adapter_receive = RequestReceiveAdapter(requests_receive)
        list_game_receive.adapter = adapter_receive
        list_game_receive.layoutManager = horizontalLayoutManagerReceive

        // --------------------//

        val horizontalLayoutManagerWaiting = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val list_game_waiting = findViewById<View>(R.id.waitingRequest) as RecyclerView

        var requests_waiting = Request.createRequest()

        val adapter_waiting = RequestWaitingAdapter(requests_waiting)
        list_game_waiting.adapter = adapter_waiting
        list_game_waiting.layoutManager = horizontalLayoutManagerWaiting
    }
}