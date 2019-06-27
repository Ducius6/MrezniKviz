package com.example.mreznikviz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mreznikviz.animations.BounceAnimation
import com.example.mreznikviz.entities.FBUser
import com.example.mreznikviz.entities.Quizz
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
        createNewQuizzButton.isEnabled = true
        createNewQuizzButton.alpha = 0.5f

        val myDataSet: MutableList<FBUser> = mutableListOf()
        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapterWaitingFriends(myDataSet)

        val quiz = intent.getSerializableExtra("quiz") as Quizz

        // TODO POSLATI OBAVIJESTI LJUDIMA


        // upise sve na firebase (admina, temu i pitanja pod idjem kviza)
        FirebaseDatabase.getInstance().reference.child("quiz/" + quiz.id + "/admin").setValue(quiz.admin)
        FirebaseDatabase.getInstance().reference.child("quiz/" + quiz.id + "/theme").setValue(quiz.questions[0].category.title)
        for (i in 1..5) {
            FirebaseDatabase.getInstance().reference.child("quiz/" + quiz.id + "/questions/" + i.toString()).setValue(quiz.questions[i-1])
        }

        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                addToList(p0.getValue(FBUser::class.java)!!) }
            override fun onChildRemoved(p0: DataSnapshot) {
                removeFromList(p0.getValue(FBUser::class.java)!!) }
            override fun onCancelled(p0: DatabaseError) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
        }
        reference.child("quiz/" + (quiz.id) + "/members").addChildEventListener(childEventListener)

        recyclerVeiwReadyFriends.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        textViewNumberOfFriends.text = myDataSet.size.toString()
        BounceAnimation(createNewQuizzButton).withAmplitude(0.4).addOnFinishListener {
            reference.child("quiz/" + quiz.id).removeEventListener(childEventListener)
            createNewQuizzButton.isEnabled = true
            createNewQuizzButton.alpha = 0.5f
            FirebaseDatabase.getInstance().reference.child("quiz/" + quiz.id + "/start").setValue(true)
            startActivity(Intent(this, QuestionActivity::class.java).putExtra("questions", quiz))
        }.enableOnTouchDemand()

    }

    fun addToList(user: FBUser) {
        createNewQuizzButton.isEnabled = true
        createNewQuizzButton.alpha = 1f
        textViewNumberOfFriends.text = (textViewNumberOfFriends.text.toString().toInt() + 1).toString()
        BounceAnimation(textViewNumberOfFriends).executeSingleEvent()
        viewAdapter.list.add(0, user)
        viewAdapter.notifyDataSetChanged()
    }

    fun removeFromList(user: FBUser) {
        if (viewAdapter.list.size <= 1) {
            createNewQuizzButton.isEnabled = true
            createNewQuizzButton.alpha = 0.5f
        }
        textViewNumberOfFriends.text = (textViewNumberOfFriends.text.toString().toInt() - 1).toString()
        BounceAnimation(textViewNumberOfFriends).withAmplitude(-0.5).executeSingleEvent()
        viewAdapter.list.remove(user)
        viewAdapter.notifyDataSetChanged()
    }
}


class MyAdapterWaitingFriends(var list: MutableList<FBUser>) : RecyclerView.Adapter<MyAdapterWaitingFriends.MyViewHolderWaitingFriends>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderWaitingFriends {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_friend_is_ready_for_quizz, parent, false) as View
        if (parent.childCount == 0)
            BounceAnimation(view).executeSingleEvent()
        else
            BounceAnimation(parent.getChildAt(0)).executeSingleEvent()
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