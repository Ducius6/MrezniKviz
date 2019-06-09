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
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_invite_people.*
import kotlin.random.Random

class CreateQuiz : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var inviteButton: Button
    private lateinit var editTextField: EditText
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var myadapter: MyUsernameAdapter
    private lateinit var listOfPeople: ArrayList<String>
    private lateinit var spinner: Spinner
    private lateinit var user: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_people)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setBackgroundDrawable( ColorDrawable(resources.getColor(R.color.colorPrimaryDark)))
        supportActionBar?.title = "QuizApp"

        recyclerView = findViewById(R.id.invitePeopleRecyclerView) as RecyclerView
        inviteButton = findViewById(R.id.inviteButton) as Button
        editTextField = findViewById(R.id.editTextInviteUser) as EditText
        spinner = findViewById(R.id.categorySpinner) as Spinner

        val user = intent.getSerializableExtra("user") as User

        //napunim adapter listom stringova sa usernameovima
        listOfPeople = arrayListOf()
        val pomoc = arrayOf(Categories.POP_MUSIC.id, Categories.FOUR_LETTER_WORDS.id,Categories.SCIENCE.id)
        //val items = arrayOf(Categories.POP_MUSIC.title, Categories.FOUR_LETTER_WORDS.title, Categories.SCIENCE.title)
        val items = arrayOf(Categories.POP_MUSIC, Categories.FOUR_LETTER_WORDS, Categories.SCIENCE)
        val filterAdapter = ArrayAdapter(this, R.layout.spinner_item, items)
        spinner.setAdapter(filterAdapter)


        inviteButton.setOnClickListener {
            val username:String = editTextField.text.toString()
            if(!username.trim().isEmpty()){
                listOfPeople.add(username)
                myadapter = MyUsernameAdapter(listOfPeople)
                recyclerView.layoutManager = viewManager
                recyclerView.adapter = myadapter
            }else{
                Toast.makeText(this,"Please enter username",Toast.LENGTH_SHORT).show()
            }
        }



        startNewQuiz.setOnClickListener {
            startNewQuiz.isEnabled = false
            lateinit var quiz : Quizz
            val id = "prvi_id_za_prvi_kviz"
            val users = listOf(User("Luka", "dsa", "asdas", "dsc", 1),
                User("Duje", "dass", "ads", "vsfdg", 0),
                User("Marin", "dfsg", "dfsdgfhg", "sdfdf", -1))
            val questions = listOf(Question(1, Category(1, "xcx"), "csf", "cxvb"),
                Question(4, Category(1, "dcv"), "scdvf", "df")
            )
            val admin = 1L



//            Thread {
//                startActivity(Intent(this, WaitingFriendsActivity::class.java).putExtra("quiz", createQuiz()))
//            }.start()



        }







    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }

//    fun createQuiz() : Quizz {
//        val jsonCategory = RestFactory.instance.getQuizzQuestions((spinner!!.selectedItem as Categories).id)
//        val key = FirebaseDatabase.getInstance().reference.child("quiz/").push().key
//
//        val questions = mutableListOf<Question>()
//        while (questions.size < 5) {
//            val randInt = Random.nextInt(jsonCategory!!.clues!!.size)
//            val question = jsonCategory.clues!![randInt]
//            if (questions.contains(question)) continue
//            questions.add(question)
//        }
//
//        val admin = user.userName
//    }
}

class MyUsernameAdapter(var list: MutableList<String>) : RecyclerView.Adapter<MyUsernameAdapter.MyInviteViewHolder>() {
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

