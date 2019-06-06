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
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_waiting_friends.*


class WaitingFriendsActivity : AppCompatActivity() {

    private lateinit var viewAdapter: MyAdapterWaitingFriends
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var quiz: Quizz

    private val QUIZ = "quiz/"
    private val PARTICIPANTS = "participants/"

    private var myDataSet = mutableListOf<User>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting_friends)

        createNewQuizzButton.isEnabled = false
        popUpMessage.animate().setDuration(1500).alpha(0f).start();

        val quiz = intent.getSerializableExtra("quiz") as Quizz
        editTextQuizTitle.text = "New Quiz by " + quiz.users.last { it.id == quiz.admin }.username

        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapterWaitingFriends(myDataSet)

        // TODO poslati obavijesti ljudima

        // stavljanje listenera na dolazak ljudi u kviz
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                if (dataSnapshot.value != null) add(dataSnapshot.getValue(User::class.java)!!) }
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.value != null) remove(dataSnapshot.getValue(User::class.java)!!) }
            override fun onChildChanged(d: DataSnapshot, p: String?) {}
            override fun onChildMoved(d: DataSnapshot, p: String?) {}
            override fun onCancelled(d: DatabaseError) {}
        }
        reference.child(QUIZ + quiz.id + PARTICIPANTS).addChildEventListener(childEventListener);

        recyclerVeiwReadyFriends.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        textViewNumberOfFriends.text = myDataSet.size.toString()

        createNewQuizzButton.setOnClickListener {
            reference.child(QUIZ + quiz.id + PARTICIPANTS).removeEventListener(childEventListener)
            createNewQuizzButton.isEnabled = false
            startActivity(Intent(this, Question::class.java))
        }
    }

    fun add(user: User) {
        createNewQuizzButton.isEnabled = true
        BounceAnimation(textViewNumberOfFriends).withAmplitude(0.5).executeSingleEvent()
        viewAdapter.list.add(0, user)
        viewAdapter.notifyDataSetChanged()
    }

    fun remove(user: User) {
        if (viewAdapter.list.size <= 1) createNewQuizzButton.isEnabled = false
        viewAdapter.list.remove(user)
        viewAdapter.notifyDataSetChanged()
    }
}


// ADAPTER
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
        holder.textView.text = user.username
    }

    class MyViewHolderWaitingFriends(customView: View) : RecyclerView.ViewHolder(customView) {
        var textView : TextView = customView.findViewById(R.id.textView)
    }
}