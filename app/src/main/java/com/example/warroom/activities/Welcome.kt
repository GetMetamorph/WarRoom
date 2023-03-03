package com.example.warroom.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warroom.R
import com.example.warroom.activities.adapters.RequestAdapter


class Welcome : AppCompatActivity(), RequestAdapter.FormRequest {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)
        val horizontalLayoutManagerAccepted = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val list_request_accepted = findViewById<View>(R.id.acceptedRequest) as RecyclerView

        var requests_accepted = Request.createRequest()

        val adapter_accepted = RequestAdapter(requests_accepted, this)
        list_request_accepted.adapter = adapter_accepted
        list_request_accepted.layoutManager = horizontalLayoutManagerAccepted


        // ---------------------//
        val horizontalLayoutManagerReceive = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val list_game_receive = findViewById<View>(R.id.receiveRequest) as RecyclerView

        var requests_receive = Request.createRequest()

        val adapter_receive = RequestAdapter(requests_receive, this)
        list_game_receive.adapter = adapter_receive
        list_game_receive.layoutManager = horizontalLayoutManagerReceive


        // --------------------//
        val horizontalLayoutManagerWaiting = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val list_game_waiting = findViewById<View>(R.id.waitingRequest) as RecyclerView

        var requests_waiting = Request.createRequest()

        val adapter_waiting = RequestAdapter(requests_waiting, this)
        list_game_waiting.adapter = adapter_waiting
        list_game_waiting.layoutManager = horizontalLayoutManagerWaiting
    }

    override fun onClick(request: Request) {
        /*val builder = AlertDialog.Builder(this)
        val alertdialogView = builder.create()
        alertdialogView.setCanceledOnTouchOutside(true)
        //val view = LayoutInflater.from(this).inflate(R.layout.receive_button, null)
        val fragment = DetailRequestFragment()
        alertdialogView.setView(fragment.view)
        alertdialogView.show()*/
        val builder = AlertDialog.Builder(this)
        val alertdialogView = builder.create()
        alertdialogView.setCanceledOnTouchOutside(true)
        val view = LayoutInflater.from(this).inflate(R.layout.receive_button, null)

        alertdialogView.setView(view)
        alertdialogView.show()
}
}