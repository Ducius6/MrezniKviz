package com.example.mreznikviz

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.mreznikviz.entities.User
import com.example.mreznikviz.usernet.UserRestFactory
import java.lang.Exception

class Login : AppCompatActivity() {
    var passwordInput: EditText? = null
    var emailInput: EditText? = null
    var loginButton: Button? = null
    var warningLogin: TextView? = null
    var createAccountButton: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val actionbar = supportActionBar
        actionbar!!.title = "Login"

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
                LoginTask().execute()
            }
        }

        createAccountButton?.setOnClickListener {
            val intent = Intent(Login@this, CreateAccount::class.java)
            startActivity(intent)
        }

    }

    fun displayWarning(){
        warningLogin?.text = "All fields must be filled"
        warningLogin?.visibility = View.VISIBLE
    }

    private inner class LoginTask: AsyncTask<Void, Void, Boolean?>() {
        override fun doInBackground(vararg params: Void): Boolean? {
            val rest = UserRestFactory.instance
            return rest.loginUser(emailInput?.text.toString(), passwordInput?.text.toString())

        }

        override fun onPostExecute(result: Boolean?) {
            if(result == true){
                val intent = Intent(this@Login, MainActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this@Login, "Wrong password or email", Toast.LENGTH_LONG).show()
            }
        }
    }
}
