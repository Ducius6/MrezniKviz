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
                CreateNewUser().execute(user)
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

    private inner class CreateNewUser: AsyncTask<User, Void, User?>() {
        override fun doInBackground(vararg params: User): User? {
            val rest = UserRestFactory.instance
            try {
                rest.registerUser(params[0])
                return params[0];
            }catch (ex:Exception){
                ex.printStackTrace()
                return params[0];
            }
        }

        override fun onPostExecute(result: User?) {
            if(result != null){
                val intent = Intent(this@CreateAccount, MainActivity::class.java)
                intent.putExtra("user", result)
                startActivity(intent)
            }
            else{
                Toast.makeText(this@CreateAccount, "Username already taken",Toast.LENGTH_LONG).show()
            }
        }
    }
}
