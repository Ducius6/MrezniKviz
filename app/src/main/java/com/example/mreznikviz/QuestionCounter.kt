package com.example.mreznikviz

import android.graphics.Color
import android.os.AsyncTask
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.mreznikviz.animations.BounceAnimation
import com.example.mreznikviz.constants.QuizzConstants
import com.example.mreznikviz.entities.Quizz
import java.util.*

class QuestionCounter(val progressBar: ProgressBar, val textViewQuestion: TextView, val category: Quizz, val button: RelativeLayout, val listOfTabs: ArrayList<RelativeLayout>, val editText: EditText): AsyncTask<Void?, Double?, Void?>(){
    var time: Double? = null
    var index: Int = 1

    override fun onPreExecute() {
        textViewQuestion.text =  category.questions!![0].question
        val bounceAnimation = BounceAnimation(button)
        bounceAnimation.addOnFinishListener{time = QuizzConstants.STANDARD_QUESTION_TIME}.enableOnTouchDemand()
    }

    override fun doInBackground(vararg params: Void?): Void? {
        time = 0.0
        markQuestion(index - 1)
        for (i in 1..QuizzConstants.STANDARD_QUESTION_NUMBER) {
            while (time!! < QuizzConstants.STANDARD_QUESTION_TIME) {
                println(time!!)
                Thread.sleep(QuizzConstants.STANDARD_TIME_STEP)
                time = time?.plus((QuizzConstants.STANDARD_TIME_STEP.toFloat()) / 1000)
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
                textViewQuestion.text = category.questions!![index].question
                evaluateQuestion(index)
                markQuestion(index)
                index += 1
            }
            progressBar.progress = (time!! * QuizzConstants.STANDARD_QUESTION_NUMBER).toInt()
        }
    }

    override fun onPostExecute(result: Void?) {

    }

    private fun evaluateQuestion(index: Int){
        if(category.questions!![index-1].answer.toLowerCase() == editText.text.toString().toLowerCase()){
            listOfTabs[index-1].setBackgroundColor(Color.GREEN)
        }
        else{
            listOfTabs[index-1].setBackgroundColor(Color.RED)
        }
    }

    private fun markQuestion(index: Int){
        editText.text.clear()
        listOfTabs[index].alpha = 1f
    }

}