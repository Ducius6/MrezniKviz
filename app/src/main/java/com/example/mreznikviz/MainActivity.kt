package com.example.mreznikviz

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.mreznikviz.entities.User
import com.example.mreznikviz.firebase.QuizFirebaseMessagingService.Companion.ACTION_RESPONSE
import com.example.mreznikviz.usernet.UserRestFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var quizId: String? = null

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user = intent.getSerializableExtra("user") as User
        dohvat.user = user;

        val broadcastReceiverIntentFilter = IntentFilter(ACTION_RESPONSE).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
        }
        val receiver = MessageBroadCastReceiver()
        registerReceiver(receiver, broadcastReceiverIntentFilter)
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("failed", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token
                FirebaseDatabase.getInstance().reference.child("tokens").child(user!!.userName).setValue(token)


                // Log and toast
                Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
                Log.e("token", token)
            })

        if (intent.extras != null) {
            val message = intent.extras?.getString("message")
            //pushmessage.text = message

        }

        Thread{
            val rest = UserRestFactory.instance
            var listaUsera:List<User> = rest.findAll(0, 10)

            runOnUiThread{
                viewManager = LinearLayoutManager(this@MainActivity)
                viewAdapter = MyAdapter(listaUsera)
                recyclerViewLeaderBoard.apply {
                    setHasFixedSize(true)
                    layoutManager = viewManager
                    adapter = viewAdapter
                }

            }
        }.start()

        createNewQuizzButton.setOnClickListener {
            startActivity(Intent(this, CreateQuiz::class.java).putExtra("user", user))
        }
    }

    fun doJoin() {
        val listener = object : ValueEventListener {
            override fun onDataChange(ds: DataSnapshot) {
                startActivity(Intent(this@MainActivity, WaitFromNotificationActivity::class.java)
                    .putExtra("id", ds.child("id").getValue(String::class.java))
                    .putExtra("admin", ds.child("admin").getValue(String::class.java))
                    .putExtra("id", quizId))
            }
            override fun onCancelled(p0: DatabaseError) {}
        }
        FirebaseDatabase.getInstance().reference.child("quiz/$quizId/info").addValueEventListener(listener)
    }

    companion object dohvat{

        private var user: User? = null

        fun getUser(): User{
            return user!!
        }
    }

    inner class MessageBroadCastReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(this@MainActivity, intent?.getStringExtra("message"), Toast.LENGTH_LONG).show()
            quizId = intent?.getStringExtra("quizId")
            val dialog = JoinDialog(this@MainActivity)
            dialog.show()
        }
    }

}

class MyAdapter(private var list: List<User>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

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
        holder.usernameTextView.text = user.userName
        holder.scoreTextView.text = user.score.toString()
    }

    class MyViewHolder(customView: View) : RecyclerView.ViewHolder(customView) {
        var noTextView: TextView = customView.findViewById(R.id.textViewNo)
        var usernameTextView: TextView = customView.findViewById(R.id.textViewUsername)
        var scoreTextView: TextView = customView.findViewById(R.id.textViewScore)
    }

}
