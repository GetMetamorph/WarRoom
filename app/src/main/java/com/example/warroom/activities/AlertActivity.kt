package com.example.warroom.activities

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.warroom.R

open class AlertActivity: AppCompatActivity() {

    protected fun showFormAlert(context: Context, title: String, validCallBack: ((Boolean) -> Unit)){
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        val alertdialogView = builder.create()

        val view= LayoutInflater.from(this).inflate(R.layout.alert_challenge_form, null)

        alertdialogView.setView(view)
        alertdialogView.window?.setBackgroundDrawableResource(R.color.transparent)


        view.findViewById<TextView>(R.id.form_title)?.let {
            it.text = title
        }

        view.findViewById<TextView>(R.id.username_label)?.let {
            it.text = context.getString(R.string.challenge_form_username)
        }

        view.findViewById<TextView>(R.id.date_label)?.let {
            it.text = context.getString(R.string.challenge_form_date)
        }

        view.findViewById<TextView>(R.id.reason_label)?.let {
            it.text = context.getString(R.string.challenge_form_reason)
        }

        view.findViewById<TextView>(R.id.description_label)?.let {
            it.text = context.getString(R.string.challenge_form_description)
        }

        view.findViewById<TextView>(R.id.valid_form_button)?.let {
            it.setOnClickListener {
                alertdialogView.dismiss()
            }
        }
        alertdialogView.show()
    }

}