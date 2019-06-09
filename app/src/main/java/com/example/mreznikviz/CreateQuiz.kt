package com.example.mreznikviz

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.mreznikviz.constants.Categories
import com.example.mreznikviz.entities.Category
import com.example.mreznikviz.entities.Question
import com.example.mreznikviz.entities.Quizz
import com.example.mreznikviz.entities.User
import kotlinx.android.synthetic.main.activity_invite_people.*

class CreateQuiz : AppCompatActivity() {
    private var recyclerView:RecyclerView? = null
    private var inviteButton:Button? = null
    private var editTextField:EditText? = null
    private var viewManager: RecyclerView.LayoutManager? = null
    private var myadapter:RecyclerView.Adapter<*>? = null
    private lateinit var listOfPeople: ArrayList<String>
    private var spinner:Spinner? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_people)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setBackgroundDrawable( ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)))
        supportActionBar?.title = "QuizApp"

        recyclerView = findViewById(R.id.invitePeopleRecyclerView) as RecyclerView
        inviteButton = findViewById(R.id.inviteButton) as Button
        editTextField = findViewById(R.id.editTextInviteUser) as EditText
        spinner = findViewById(R.id.categorySpinner) as Spinner

        //napunim adapter listom stringova sa usernameovima
        listOfPeople = arrayListOf()
        val pomoc = arrayOf(Categories.POP_MUSIC.id, Categories.FOUR_LETTER_WORDS.id,Categories.SCIENCE.id)
        val items = arrayOf(Categories.POP_MUSIC.title, Categories.FOUR_LETTER_WORDS.title, Categories.SCIENCE.title)
        val filterAdapter = ArrayAdapter(this, R.layout.spinner_item, items)
        spinner!!.setAdapter(filterAdapter)


        inviteButton!!.setOnClickListener {
            var username:String = editTextField!!.text.toString()
            if(!username.trim().isEmpty()){
                listOfPeople.add(username)
                myadapter = MyUsernameAdapter(listOfPeople)
                recyclerView!!.layoutManager = viewManager
                recyclerView!!.adapter = myadapter
            }else{
                Toast.makeText(this,"Please enter username",Toast.LENGTH_SHORT).show()
            }
        }



        startNewQuiz.setOnClickListener {
            lateinit var quiz : Quizz
            val id = "prvi_id_za_prvi_kviz"
            val users = listOf(User("Luka", "dsa", "asdas", "dsc", 1),
                User("Duje", "dass", "ads", "vsfdg", 0),
                User("Marin", "dfsg", "dfsdgfhg", "sdfdf", -1))
            val questions = listOf(
                Question(1, Category(1, "prvo pitanje"), "prvo pitanje", "1"),
                Question(2, Category(2, "drugo pitaje"), "drugo pitaje", "2"),
                Question(3, Category(3, "trece pitanje"), "trece pitanje", "3"),
                Question(4, Category(4, "cetvrto pitanje"), "cetvrto pitanje", "4"),
                Question(5, Category(5, "peto pitanje"), "peto pitanje", "5")
            )
            val admin = 1L

            quiz = Quizz(id, users, questions, admin = admin)

            var intent: Intent = Intent(this, WaitingFriendsActivity::class.java).putExtra("quiz", quiz)
            startActivity(intent)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }
}

class MyUsernameAdapter(var list: List<String>) : RecyclerView.Adapter<MyUsernameAdapter.MyInviteViewHolder>() {
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

