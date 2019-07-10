package com.example.mreznikviz

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mreznikviz.entities.Quizz
import com.example.mreznikviz.usernet.UserRestFactory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_final.*

class FinalActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final)

        val myUsername = intent.getStringExtra("userName")
        val myScore = intent.getIntExtra("score", 0)
        val quiz = intent.getSerializableExtra("quiz") as Quizz
        val nop = intent.getLongExtra("nop", 0L)

        Log.d("NOP", nop.toString())

        val viewManager = LinearLayoutManager(this)
        val viewAdapter = MyAdapterFinal(mutableListOf(), mutableListOf())
        recyclerViewLeaderBoard.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        textView.text = "Leaderboard - " + quiz.admin + "'s Quiz"
        createNewQuizButton.isEnabled = false
        createNewQuizButton.alpha = 0.4f

        FirebaseDatabase.getInstance().reference.child("quiz/" + quiz.id +"/leaderboard/$myUsername").setValue(myScore)

        //TODO trenutno prikazi neki loading dok ne rjese svi

        val listener = object : ValueEventListener {
            override fun onDataChange(ds: DataSnapshot) {
                if (ds.childrenCount == nop) {
                    Log.d("NOP2", "USLO")
                    //TODO makni loading
                    var i = 0
                    for (d in ds.children.sortedBy { it.getValue(Int::class.java) }) {
                        viewAdapter.userNames.add(i, d.key!!)
                        viewAdapter.scores.add(i, d.getValue(Int::class.java)!!)
                        i += 1
                        if (MainActivity.getUser()?.userName.equals(d.key)) {
                            Thread {
                                Log.d("BROJ BODOVA", d.getValue(Int::class.java)!!.toString())
                                UserRestFactory.instance.updateUser(d.getValue(Int::class.java)!!.toLong(), d.key!!)
                            }.start()
                        }
                    }
                    viewAdapter.notifyDataSetChanged()
                    createNewQuizButton.isEnabled = true
                    createNewQuizButton.alpha = 1f
                }

            }
            override fun onCancelled(p0: DatabaseError) {}
        }
        FirebaseDatabase.getInstance().reference.child("quiz/" + quiz.id +"/leaderboard").addValueEventListener(listener)

        createNewQuizButton.setOnClickListener { startActivity(Intent(this, MainActivity::class.java).putExtra("user", MainActivity.getUser())) }
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