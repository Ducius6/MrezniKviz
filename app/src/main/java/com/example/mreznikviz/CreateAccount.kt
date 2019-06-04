package com.example.mreznikviz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class CreateAccount : AppCompatActivity() {

    var nameInput: EditText? = null
    var emailInput: EditText? = null
    var usernameInput: EditText? = null
    var passwordInput: EditText? = null
    var createButton: Button? = null
    var warningCreate: TextView? = null

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val actionbar = supportActionBar
        actionbar!!.title = "Create Account"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        nameInput = findViewById(R.id.editTextName)
        emailInput = findViewById(R.id.editTextEmailCreate)
        usernameInput = findViewById(R.id.textViewUsername)
        passwordInput = findViewById(R.id.edittextPasswordCreate)
        createButton = findViewById(R.id.createButton)
        warningCreate = findViewById(R.id.warningCreate)


        createButton?.setOnClickListener {
            if (nameInput?.text.toString().isEmpty()
                || passwordInput?.text.toString().isEmpty()
                || usernameInput?.text.toString().isEmpty()
                || emailInput?.text.toString().isEmpty()
            ) {
                displayWarning()
            } else {
                val intent = Intent(CreateAccount@ this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun displayWarning() {
        warningCreate?.text = "All fields must be filled"
        warningCreate?.visibility = View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
