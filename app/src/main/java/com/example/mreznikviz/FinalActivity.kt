package com.example.mreznikviz

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mreznikviz.entities.Quizz
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_final.*

class FinalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final)

        val myUsername = intent.getStringExtra("userName")
        val myScore = intent.getIntExtra("score", 0)
        val quiz = intent.getSerializableExtra("quiz") as Quizz
        val nop = intent.getLongExtra("nop", 0L)

        val viewManager = LinearLayoutManager(this)
        val viewAdapter = MyAdapterFinal(mutableListOf(), mutableListOf())
        recyclerViewLeaderBoard.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        FirebaseDatabase.getInstance().reference.child("quiz/" + quiz.id +"/leaderboard/$myUsername").setValue(myScore)

        //TODO trenutno prikazi neki loading dok ne rjese svi

        val listener = object : ValueEventListener {
            override fun onDataChange(ds: DataSnapshot) {
                if (ds.childrenCount == nop) {
                    //TODO makni loading
                    var i = 0
                    for (d in ds.children.sortedBy { it.getValue(Int::class.java) }) {
                        viewAdapter.userNames.add(i, d.key!!)
                        viewAdapter.scores.add(i, d.getValue(Int::class.java)!!)
                        i += 1
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError) {}
        }
    }
}

class MyAdapterFinal(var userNames: MutableList<String>, var scores: MutableList<Int>) : RecyclerView.Adapter<MyAdapterFinal.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_leader_board_element, parent, false) as View
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userNames.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {
        holder.noTextView.text = (i + 1).toString() + "."
        holder.usernameTextView.text = userNames[i]
        holder.scoreTextView.text = scores[i].toString()
    }

    class MyViewHolder(customView: View) : RecyclerView.ViewHolder(customView) {
        var noTextView: TextView = customView.findViewById(R.id.textViewNo)
        var usernameTextView: TextView = customView.findViewById(R.id.textViewUsername)
        var scoreTextView: TextView = customView.findViewById(R.id.textViewScore)
    }

}