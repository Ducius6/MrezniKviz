package com.example.mreznikviz

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.example.mreznikviz.animations.BounceAnimation
import com.example.mreznikviz.constants.Categories
import com.example.mreznikviz.entities.Question
import com.example.mreznikviz.entities.Quizz
import com.example.mreznikviz.entities.User
import com.example.mreznikviz.quiznet.RestFactory
import com.example.mreznikviz.usernet.UserRestFactory
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_invite_people.*
import kotlin.random.Random

class CreateQuiz : AppCompatActivity() {
    private lateinit var recyclerViewAll: RecyclerView
    private lateinit var recyclerViewSelected : RecyclerView
    private lateinit var viewManager1: RecyclerView.LayoutManager
    private lateinit var viewManager2: RecyclerView.LayoutManager
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

        user = intent.getSerializableExtra("user") as User


        viewManager1 = LinearLayoutManager(this)
        viewManager2 = LinearLayoutManager(this)


        recyclerViewAll = findViewById(R.id.invitePeopleRecyclerView)
        recyclerViewSelected = findViewById(R.id.addedPeopleRecyclerView)

        selectedAdapter = MyAdapterSelected(mutableListOf())
        allAdapter = MyAdapterAll(mutableListOf(User("","Load more", "", "", 0)), this, user)

        selectedAdapter.allAdapter = allAdapter
        allAdapter.selectedAdapter = selectedAdapter

        recyclerViewAll.apply {
            setHasFixedSize(true)
            layoutManager = viewManager1
            adapter = allAdapter
        }
        recyclerViewSelected.apply {
            setHasFixedSize(true)
            layoutManager = viewManager2
            adapter = selectedAdapter
        }

        // SPINNER
        spinner = findViewById(R.id.categorySpinner)
        val items = arrayOf(Categories.POP_MUSIC, Categories.FOUR_LETTER_WORDS, Categories.SCIENCE, Categories.SPORT)
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
        return Quizz(key!!, listOfUsers, questions, admin, jsonCategory?.title!!)
    }
}

class MyAdapterSelected(var list: MutableList<User>) : RecyclerView.Adapter<MyAdapterSelected.MyInviteViewHolder>() {

    var allAdapter : MyAdapterAll? = null

    override fun onBindViewHolder(holder: MyAdapterSelected.MyInviteViewHolder, i: Int) {
        val text = list[i].userName
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
        BounceAnimation(view).withAmplitude(0.3).executeSingleEvent()
        return MyInviteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class MyInviteViewHolder(customView: View) : RecyclerView.ViewHolder(customView) {
        var username: TextView = customView.findViewById(R.id.textViewUsername)
    }
}

class MyAdapterAll(var list: MutableList<User>, val activity: CreateQuiz, val user : User) : RecyclerView.Adapter<MyAdapterAll.MyInviteViewHolder>() {

    var selectedAdapter: MyAdapterSelected? = null
    var count = 0

    init {
        Thread {
            val users = UserRestFactory.instance.findAll(count, 10).toMutableList()
            users.removeAll { it.userName.equals(user.userName) }
            count += users.size
            activity.runOnUiThread {
                list.addAll(list.size - 1, users)
                notifyDataSetChanged()
            }
        }.start()
    }


    override fun onBindViewHolder(holder: MyAdapterAll.MyInviteViewHolder, i: Int) {
        val text = list[i].userName
        holder.username.text = text



        holder.username.setOnClickListener {
            if (i == list.size - 1) {
                Thread {
                    val users = UserRestFactory.instance.findAll(count, 10).toMutableList()
                    users.removeAll { it.userName.equals(user.userName) }
                    count += users.size
                    activity.runOnUiThread {
                        if (users.isEmpty()) {
                            list[list.size - 1].userName = "All users loaded"
                        }
                        list.addAll(list.size - 1, users)
                        notifyDataSetChanged()
                    }
                }.start()
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