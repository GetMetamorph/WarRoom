package com.example.warroom.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.warroom.R
import com.example.warroom.viewModels.UserViewModel

//TODO: create view model to check if user exit with username
open class AlertActivity: AppCompatActivity() {
    private var alertView: View? = null
    private var alertDialogView: AlertDialog? = null
    private var userViewModel = UserViewModel()

    protected fun showFormAlert(context: Context, title: String, validCallBack: ((Boolean) -> Unit)){
        val builder = AlertDialog.Builder(context)
        val alertdialogView = builder.create()
        alertdialogView.setCanceledOnTouchOutside(true)

        val view = LayoutInflater.from(context).inflate(R.layout.alert_challenge_form, null)
        this.alertView = view

        alertdialogView.setView(view)
        this.alertDialogView = alertdialogView
        alertdialogView.window?.setBackgroundDrawableResource(R.color.transparent)

        val formTitle = view.findViewById<TextView>(R.id.form_title)
        val username = view.findViewById<TextView>(R.id.username_label)
        val date = view.findViewById<TextView>(R.id.date_label)
        val reason = view.findViewById<TextView>(R.id.reason_label)
        val description = view.findViewById<TextView>(R.id.description_label)
        val validationButton = view.findViewById<Button>(R.id.valid_form_button)


        formTitle?.let {
            it.text = title
        }

        username?.let {
            it.text = context.getString(R.string.challenge_form_username)
        }

        date?.let {
            it.text = context.getString(R.string.challenge_form_date)
        }

        reason?.let {
            it.text = context.getString(R.string.challenge_form_reason)
        }

        description?.let {
            it.text = context.getString(R.string.challenge_form_description)
        }

        validationButton?.let {
            it.setOnClickListener {
                this.checkFields(
                    username.text.toString(),
                    date.text.toString(),
                    reason.text.toString(),
                    description.text.toString()
                )
            }
        }
        alertdialogView.show()
    }

    private fun checkFields(username: String, date: String, reason: String, description: String) {

        //TODO: must check :
        //      - if the username is in the api
        //      - date is a date

        this.alertView = null
        alertDialogView?.dismiss()
        this.alertDialogView = null
    }

}