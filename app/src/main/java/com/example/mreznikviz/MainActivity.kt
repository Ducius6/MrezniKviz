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
import com.example.mreznikviz.entities.User

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myDataset = listOf(User(1, "Luka", "lkm", "email", "pass",100),
            User(2, "Duje", "ducius", "email", "pass", 20),
            User(3, "Marin", "mara-legenda", "email", "pass", 25)
        )

        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(myDataset)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewLeaderBoard).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        createNewQuizzButton.setOnClickListener { startActivity(Intent(this, InvitePeopleActivity::class.java)) }
    }
}

class MyAdapter(var list: List<User>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_leader_board_element, parent, false) as View
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {
        val user = list[i]
        holder.noTextView.text = (i + 1).toString() + "."
        holder.usernameTextView.text = user.username
        holder.scoreTextView.text = user.score.toString()
    }

    class MyViewHolder(customView: View) : RecyclerView.ViewHolder(customView) {
        var noTextView: TextView = customView.findViewById(R.id.textViewNo)
        var usernameTextView: TextView = customView.findViewById(R.id.textViewUsername)
        var scoreTextView: TextView = customView.findViewById(R.id.textViewScore)
    }
}
