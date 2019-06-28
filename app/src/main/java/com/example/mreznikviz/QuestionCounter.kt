package com.example.mreznikviz

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.mreznikviz.animations.BounceAnimation
import com.example.mreznikviz.constants.QuizzConstants
import com.example.mreznikviz.entities.Quizz
import java.security.AccessControlContext
import java.util.*
import kotlin.math.round

class QuestionCounter(val progressBar: ProgressBar, val textViewQuestion: TextView, val category: Quizz, val button: RelativeLayout, val listOfTabs: ArrayList<RelativeLayout>, val editText: EditText, val context: Activity): AsyncTask<Void?, Double?, Void?>(){
    var time: Double = 0.0
    var index: Int = 1

    var listAnswers = arrayListOf(false, false, false, false, false)
    var listTimes = arrayListOf(0, 0, 0, 0, 0)

    override fun onPreExecute() {
        textViewQuestion.text =  category.questions[0].question
        val bounceAnimation = BounceAnimation(button)
        bounceAnimation.addOnFinishListener{listTimes[index - 1] = time.toInt(); time = QuizzConstants.STANDARD_QUESTION_TIME}.enableOnTouchDemand()
    }

    override fun doInBackground(vararg params: Void?): Void? {
        time = 0.0
        markQuestion(index - 1)
        for (i in 1..QuizzConstants.STANDARD_QUESTION_NUMBER) {
            while (time < QuizzConstants.STANDARD_QUESTION_TIME) {
                println(time)
                Thread.sleep(QuizzConstants.STANDARD_TIME_STEP)
                time = time.plus((QuizzConstants.STANDARD_TIME_STEP.toFloat()) / 1000)
                publishProgress(time)
            }
            if(index!=QuizzConstants.STANDARD_QUESTION_NUMBER) {
                time = 0.0
                progressBar.progress = 0
            }
            else{
                evaluateQuestion(index)
                break
            }
        }
        return null
    }

    override fun onProgressUpdate(vararg values: Double?) {
        for (value in values) {
            if(value!! > QuizzConstants.STANDARD_QUESTION_TIME && index<QuizzConstants.STANDARD_QUESTION_NUMBER) {
                textViewQuestion.text = category.questions[index].question
                evaluateQuestion(index)
                markQuestion(index)
                index += 1
            }
            progressBar.progress = (time * QuizzConstants.STANDARD_QUESTION_NUMBER).toInt()
        }
    }

    override fun onPostExecute(result: Void?) {
        val brojBodova = 0
        for (i in 1..QuizzConstants.STANDARD_QUESTION_NUMBER) {
            if (listAnswers[i - 1])
                brojBodova.plus(30 - listTimes[i - 1])
        }
        context.startActivity(Intent(context, FinalActivity::class.java)
            .putExtra("quiz", category)
            .putExtra("score", brojBodova)
            .putExtra("userName", MainActivity.getUser().userName)
            .putExtra("nop", context.intent.getLongExtra("nop", 0L)))

    }

    private fun evaluateQuestion(index: Int){
        if(category.questions[index-1].answer.toLowerCase() == editText.text.toString().toLowerCase()){
            listOfTabs[index-1].setBackgroundResource(R.drawable.background_green)
            listAnswers[index - 1] = true
        }
        else{
            listOfTabs[index-1].setBackgroundResource(R.drawable.background_red)
        }
    }

    private fun markQuestion(index: Int){
        editText.text.clear()
        listOfTabs[index].alpha = 1f
    }

}