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
import android.widget.EditText
import android.widget.TextView
import com.example.mreznikviz.entities.User
import kotlinx.android.synthetic.main.activity_waiting_friends.*


class WaitingFriendsActivity : AppCompatActivity() {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting_friends)

        val myDataSet = listOf(User(1, "Luka", "lkm", "email", "pass",100),
            User(2, "Duje", "ducius", "email", "pass", 20),
            User(3, "Marin", "mara-legenda", "email", "pass", 25)
        )

        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapterWaitingFriends(myDataSet)

        recyclerVeiwReadyFriends.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        textViewNumberOfFriends.text = myDataSet.size.toString()

        createNewQuizzButton.setOnClickListener { startActivity(Intent(this, Question::class.java)) }
    }
}


class MyAdapterWaitingFriends(private var list: List<User>) : RecyclerView.Adapter<MyAdapterWaitingFriends.MyViewHolderWaitingFriends>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderWaitingFriends {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_friend_is_ready_for_quizz, parent, false) as View
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