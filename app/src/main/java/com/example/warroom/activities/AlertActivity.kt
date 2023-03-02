package com.example.warroom.activities

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.warroom.R

open class AlertActivity: AppCompatActivity() {
    private var alertView: View? = null
    private var alertDialogView: AlertDialog? = null

    protected fun showFormAlert(context: Context, title: String, validCallBack: ((Boolean) -> Unit)){
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        val alertdialogView = builder.create()
        alertdialogView.setCanceledOnTouchOutside(true)

        val view = LayoutInflater.from(this).inflate(R.layout.alert_challenge_form, null)
        this.alertView = view

        alertdialogView.setView(view)
        this.alertDialogView = alertdialogView
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

        view.findViewById<Button>(R.id.valid_form_button)?.let {
            it.setOnClickListener {
                this.alertView = null
                this.alertDialogView = null
                alertdialogView.dismiss()
            }
        }
        alertdialogView.show()
    }

}