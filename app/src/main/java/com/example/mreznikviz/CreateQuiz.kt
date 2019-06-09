package com.example.mreznikviz

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.mreznikviz.constants.Categories
import com.example.mreznikviz.entities.*
import com.example.mreznikviz.quiznet.RestFactory
import com.example.mreznikviz.usernet.UserRestFactory
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_invite_people.*
import kotlin.random.Random

class CreateQuiz : AppCompatActivity() {
    private lateinit var recyclerViewAll: RecyclerView
    private lateinit var recyclerViewSelected : RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var selectedAdapter: MyAdapterSelected
    private lateinit var allAdapter: MyAdapterAll
    private lateinit var spinner: Spinner
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_people)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setBackgroundDrawable( ColorDrawable(resources.getColor(R.color.colorPrimaryDark)))
        supportActionBar?.title = "QuizApp"


        viewManager = LinearLayoutManager(this)

        recyclerViewAll = findViewById(R.id.invitePeopleRecyclerView)
        recyclerViewSelected = findViewById(R.id.addedPeopleRecyclerView)

        selectedAdapter = MyAdapterSelected(mutableListOf())
        allAdapter = MyAdapterAll(mutableListOf())

        recyclerViewAll.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = allAdapter
        }
        recyclerViewAll.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = selectedAdapter
        }


        val user = intent.getSerializableExtra("user") as User



        // SPINNER
        spinner = findViewById(R.id.categorySpinner)
        val items = arrayOf(Categories.POP_MUSIC, Categories.FOUR_LETTER_WORDS, Categories.SCIENCE)
        val filterAdapter = ArrayAdapter(this, R.layout.spinner_item, items)
        spinner.adapter = filterAdapter






        startNewQuiz.setOnClickListener {
            startNewQuiz.isEnabled = false
            Thread {
                val quizz = createQuiz(selectedAdapter.list)
                runOnUiThread {
                    startActivity(Intent(this, WaitingFriendsActivity::class.java).putExtra("quiz", quizz))
                }
            }.start()
        }

    }






    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createQuiz(listOfUsers : List<User>) : Quizz {
        val jsonCategory = RestFactory.instance.getQuizzQuestions((spinner.selectedItem as Categories).id)
        val key = FirebaseDatabase.getInstance().reference.child("quiz/").push().key
        val questions = mutableListOf<Question>()
        while (questions.size < 5) {
            val randInt = Random.nextInt(jsonCategory!!.clues!!.size)
            val question = jsonCategory.clues!![randInt]
            if (questions.contains(question)) continue
            questions.add(question)
        }
        val admin = user.userName
        return Quizz(key!!, listOfUsers, questions, admin)
    }
}

class MyAdapterSelected(var list: MutableList<User>) : RecyclerView.Adapter<MyAdapterSelected.MyInviteViewHolder>() {

    var allAdapter : MyAdapterAll? = null

    override fun onBindViewHolder(holder: MyAdapterSelected.MyInviteViewHolder, i: Int) {
        val text = list.get(i).userName
        holder.username.text = text

        holder.username.setOnClickListener {
            allAdapter!!.list.add(allAdapter!!.list.size - 1, list[i])
            allAdapter!!.notifyDataSetChanged()
            list.removeAt(i)
            notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyInviteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_leader_board_element, parent, false) as View
        return MyInviteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class MyInviteViewHolder(customView: View) : RecyclerView.ViewHolder(customView) {
        var username: TextView = customView.findViewById(R.id.textViewUsername)
    }
}

class MyAdapterAll(var list: MutableList<User>) : RecyclerView.Adapter<MyAdapterAll.MyInviteViewHolder>() {

    var selectedAdapter: MyAdapterSelected? = null
    var count = 0

    override fun onBindViewHolder(holder: MyAdapterAll.MyInviteViewHolder, i: Int) {
        val text = list.get(i).userName
        holder.username.text = text

        holder.username.setOnClickListener {
            if (i == list.size - 1) {
                val users = UserRestFactory.instance.findAll(i, 10)
                count += users.size
                list.addAll(list.size - 1, users)
                notifyDataSetChanged()
            } else  {
                selectedAdapter!!.list.add(list[i])
                selectedAdapter!!.notifyDataSetChanged()
                list.removeAt(i)
                notifyDataSetChanged()
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyInviteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_leader_board_element, parent, false) as View
        return MyInviteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class MyInviteViewHolder(customView: View) : RecyclerView.ViewHolder(customView) {
        var username: TextView = customView.findViewById(R.id.textViewUsername)
    }
}