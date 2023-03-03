package com.example.warroom.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.warroom.R

class DetailRequestActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.receive_button)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, DetailRequestFragment())
            .commitAllowingStateLoss()
    }
}

class DetailRequestFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.detail_request_fragment, container, true)
    }

    override fun onViewCreated(view: View,
                               savedInstanceState: Bundle?) {
        val buttonCancel = view.findViewById<Button>(R.id.cancel)
        val buttonAccept = view.findViewById<Button>(R.id.accept)
        val buttonRefuse = view.findViewById<Button>(R.id.refuse)

        buttonCancel.setOnClickListener {
            Toast.makeText(context, "Ah ouais t'as perdu tes couilles", Toast.LENGTH_LONG).show()
        }

        buttonAccept.setOnClickListener {
            Toast.makeText(context, "Je vais te briser les os et boire ton sang", Toast.LENGTH_LONG).show()
        }

        buttonRefuse.setOnClickListener {
            Toast.makeText(context, "Pas le temps", Toast.LENGTH_LONG).show()
        }
    }
}