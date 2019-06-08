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

class Login : AppCompatActivity() {
    var passwordInput: EditText? = null
    var userNameInput: EditText? = null
    var loginButton: Button? = null
    var warningLogin: TextView? = null
    var createAccountButton: Button? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val actionbar = supportActionBar
        actionbar!!.title = "Login"

        passwordInput = findViewById(R.id.editTextPasswordLogin)
        userNameInput = findViewById(R.id.editTextUsernameLogin)
        loginButton = findViewById(R.id.loginButton)
        warningLogin = findViewById(R.id.warningLogin)
        createAccountButton = findViewById(R.id.createAccountButton);

        loginButton?.setOnClickListener {
            if(passwordInput?.text.toString().isEmpty() || userNameInput?.text.toString().isEmpty()){
                displayWarning()
            }
            else{
                Thread{
                    val rest = UserRestFactory.instance
                    val user:User? = rest.loginUser( userNameInput?.text.toString(), passwordInput?.text.toString())
                    Log.d("usernull",user?.userName.toString())
                    runOnUiThread {
                        if(user != null){
                            val intent = Intent(this@Login, MainActivity::class.java)
                            intent.putExtra("user",user)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this@Login,"Wrong email or password", Toast.LENGTH_LONG).show()
                        }
                    }
                }.start()
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

}
