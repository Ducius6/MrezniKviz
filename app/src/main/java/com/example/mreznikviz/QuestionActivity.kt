package com.example.mreznikviz

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.mreznikviz.animations.BounceAnimation
import com.example.mreznikviz.entities.Quizz
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity() {

    var progressBar: ProgressBar? = null
    var counter: QuestionCounter? = null
    var textViewQuestion: TextView?  = null
    var button: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        var category = intent.getSerializableExtra("questions") as Quizz

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels

        val raz = (width.toFloat()) / 31

        val line = raz * 5


        progressBar = findViewById(R.id.progressBarQuestion)
        progressBar?.progress = 0

        button = findViewById(R.id.nextButton)
        val textCounter = findViewById<TextView>(R.id.startCounter)

        val tabOne: RelativeLayout = findViewById(R.id.questionOneTab)
        val tabTwo: RelativeLayout = findViewById(R.id.questionTwoTab)
        val tabThree: RelativeLayout = findViewById(R.id.questionThreeTab)
        val tabFour: RelativeLayout = findViewById(R.id.questionFourTab)
        val tabFive: RelativeLayout = findViewById(R.id.questionFiveTab)

        val editText: EditText= findViewById(R.id.editTextAnswer)

        val listOfTabs = arrayListOf(tabOne, tabTwo, tabThree, tabFour, tabFive)

        for (tab in listOfTabs){
            tab.layoutParams.width = line.toInt()
            (tab.layoutParams as RelativeLayout.LayoutParams).leftMargin = raz.toInt()
            (tab.layoutParams as RelativeLayout.LayoutParams).rightMargin = 0
        }

        (tabFive.layoutParams as RelativeLayout.LayoutParams).leftMargin = raz.toInt()

        textViewQuestion = this.findViewById(R.id.textViewQuestion)

        counter = QuestionCounter(progressBar!!, textViewQuestion!!, category, nextButton, listOfTabs, editText, this)

        Thread(Runnable {
            for ( i in 0..3){
                runOnUiThread {
                    BounceAnimation(textCounter).withDuration(400).executeSingleEvent()
                    if(i == 3){
                        textCounter.background = null
                        textCounter.setTextColor(resources.getColor(R.color.white))
                        textCounter.text = "START"
                    }
                    else{
                        textCounter.text = (3 - i).toString()
                    }
                }
                Thread.sleep(1000)
            }
            runOnUiThread {
                textCounter.visibility = View.GONE

                textViewQuestion?.alpha = 1f

                counter?.execute()
            }
        }).start()

    }
}
