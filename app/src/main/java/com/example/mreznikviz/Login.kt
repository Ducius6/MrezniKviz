package com.example.mreznikviz

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class Login : AppCompatActivity() {
    var passwordInput: EditText? = null
    var emailInput: EditText? = null
    var loginButton: Button? = null
    var warningLogin: TextView? = null
    var createAccountButton: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        passwordInput = findViewById(R.id.editTextPasswordLogin)
        emailInput = findViewById(R.id.editTextEmailLogin)
        loginButton = findViewById(R.id.loginButton)
        warningLogin = findViewById(R.id.warningLogin)
        createAccountButton = findViewById(R.id.createAccountButton);

        loginButton?.setOnClickListener {
            if(passwordInput?.text.toString().isEmpty() || emailInput?.text.toString().isEmpty()){
                displayWarning()
            }
            else{
                val intent = Intent(Login@this, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }

    fun displayWarning(){
        warningLogin?.text = "All fields must be filled"
        warningLogin?.visibility = View.VISIBLE
    }
}
