package com.example.mreznikviz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.example.mreznikviz.entities.User
import com.example.mreznikviz.usernet.UserRestFactory

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

        nameInput = findViewById(R.id.editTextName) as EditText
        emailInput = findViewById(R.id.editTextEmailCreate) as EditText
        usernameInput = findViewById(R.id.editTextUsername) as EditText
        passwordInput = findViewById(R.id.edittextPasswordCreate) as EditText
        createButton = findViewById(R.id.createButton)  as Button
        warningCreate = findViewById(R.id.warningCreate)


        createButton?.setOnClickListener {
            if (nameInput?.text.toString().isEmpty()
                || passwordInput?.text.toString().isEmpty()
                || usernameInput?.text.toString().isEmpty()
                || emailInput?.text.toString().isEmpty()
            ) {
                displayWarning()
            } else {
                val user = User(nameInput!!.text.toString(),usernameInput!!.text.toString(),emailInput!!.text.toString(),passwordInput!!.text.toString(), 0 )
                Thread{
                    val rest = UserRestFactory.instance
                    try {
                        rest.registerUser(user)
                        val intent = Intent(this@CreateAccount, MainActivity::class.java)
                        intent.putExtra("user", user)
                        startActivity(intent)
                    }catch (ex:Exception){
                        ex.printStackTrace()
                        runOnUiThread {
                            Toast.makeText(this@CreateAccount, "Username already taken",Toast.LENGTH_LONG).show()
                        }
                    }
                }.start()
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
