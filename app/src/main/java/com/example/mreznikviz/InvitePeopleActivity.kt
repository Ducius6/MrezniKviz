package com.example.mreznikviz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.mreznikviz.entities.User

class InvitePeopleActivity : AppCompatActivity() {
    private var recyclerView:RecyclerView? = null
    private var inviteButton:Button? = null
    private var editTextField:EditText? = null
    private var viewManager: RecyclerView.LayoutManager? = null
    private var myadapter:RecyclerView.Adapter<*>? = null
    private lateinit var listOfPeople: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_people)

        recyclerView = findViewById(R.id.invitePeopleRecyclerView) as RecyclerView
        inviteButton = findViewById(R.id.inviteButton) as Button
        editTextField = findViewById(R.id.editTextInviteUser) as EditText

        //napunim adapter listom stringova sa usernameovima
        listOfPeople = arrayListOf()

        inviteButton!!.setOnClickListener {
            var username:String = editTextField!!.text.toString()
            listOfPeople.add(username)
            myadapter = MyUsernameAdapter(listOfPeople)
            viewManager = LinearLayoutManager(this)
            recyclerView!!.layoutManager = viewManager
            recyclerView!!.adapter = myadapter
        }


    }
}

class MyUsernameAdapter(var list: List<String>) : RecyclerView.Adapter<MyUsernameAdapter.MyInviteViewHolder>() {
    override fun onBindViewHolder(holder: MyUsernameAdapter.MyInviteViewHolder, i: Int) {
        val text = list.get(i)
        holder.username.text = text    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyInviteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_invite_row, parent, false) as View
        return MyInviteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class MyInviteViewHolder(customView: View) : RecyclerView.ViewHolder(customView) {
        var username: TextView = customView.findViewById(R.id.inviteUsername)
    }
}

