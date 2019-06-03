package com.example.mreznikviz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText

class InvitePeopleActivity : AppCompatActivity() {
    private var recyclerView:RecyclerView? = null
    private var inviteButton:Button? = null
    private var editTextField:EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_people)

        recyclerView = findViewById(R.id.invitePeopleRecyclerView) as RecyclerView
        inviteButton = findViewById(R.id.inviteButton) as Button
        editTextField = findViewById(R.id.editTextInviteUser) as EditText

        var adapter:ArrayAdapter<String>
        //napunim adapter listom stringova sa usernameovima
        var listOfPeople:ArrayList<String> = arrayListOf()

        inviteButton!!.setOnClickListener {
            var username:String = editTextField!!.text.toString()
            listOfPeople.add(username)
        }


    }
}
