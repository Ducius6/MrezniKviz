package com.example.mreznikviz

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mreznikviz.animations.BounceAnimation
import com.example.mreznikviz.entities.Quizz
import com.example.mreznikviz.entities.User
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_waiting_friends.*


class WaitingFriendsActivity : AppCompatActivity() {

    private lateinit var viewAdapter: MyAdapterWaitingFriends
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val reference = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting_friends)
        createNewQuizzButton.isEnabled = false

        val myDataSet: MutableList<User> = mutableListOf()
        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapterWaitingFriends(myDataSet)

        val quiz = intent.getSerializableExtra("quiz") as Quizz

        // TODO POSLATI OBAVIJESTI LJUDIMA

        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                addToList(p0.getValue(User::class.java)!!) }
            override fun onChildRemoved(p0: DataSnapshot) {
                removeFromList(p0.getValue(User::class.java)!!) }
            override fun onCancelled(p0: DatabaseError) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
        }
        reference.child("quiz/" + quiz.id).addChildEventListener(childEventListener)

        recyclerVeiwReadyFriends.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        textViewNumberOfFriends.text = myDataSet.size.toString()

        createNewQuizzButton.setOnClickListener {
            reference.child("quiz/" + quiz.id).removeEventListener(childEventListener)
            createNewQuizzButton.isEnabled = false
            startActivity(Intent(this, Question::class.java).putExtra("questions", quiz))
        }
    }

    fun addToList(user: User) {
        createNewQuizzButton.isEnabled = true
        BounceAnimation(textViewNumberOfFriends).executeSingleEvent()
        viewAdapter.list.add(0, user)
        viewAdapter.notifyDataSetChanged()
    }

    fun removeFromList(user: User) {
        if (viewAdapter.list.size <= 1) createNewQuizzButton.isEnabled = false
        viewAdapter.list.remove(user)
        viewAdapter.notifyDataSetChanged()
    }
}


class MyAdapterWaitingFriends(var list: MutableList<User>) : RecyclerView.Adapter<MyAdapterWaitingFriends.MyViewHolderWaitingFriends>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderWaitingFriends {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_friend_is_ready_for_quizz, parent, false) as View
        BounceAnimation(view).executeSingleEvent()
        return MyViewHolderWaitingFriends(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolderWaitingFriends, i: Int) {
        val user = list[i]
        holder.textView.text = user.userName
    }

    class MyViewHolderWaitingFriends(customView: View) : RecyclerView.ViewHolder(customView) {
        var textView : TextView = customView.findViewById(R.id.textView)
    }
}