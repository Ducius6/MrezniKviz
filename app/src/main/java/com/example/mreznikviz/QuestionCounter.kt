package com.example.mreznikviz

import android.os.AsyncTask
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.example.mreznikviz.entities.JsonCategory

public class QuestionCounter(val progressBar: ProgressBar, val textViewQuestion: TextView, val category: JsonCategory, val button: Button): AsyncTask<Void?, Double?, Void?>(){
    var time: Double? = null
    var index: Int = 1
    var animateProgressBar = AnimateProgressBar()

    override fun doInBackground(vararg params: Void?): Void? {
        textViewQuestion.text =  category.clues!![0].question
        button.setOnClickListener {
            
        }
        time = 0.0
        for (i in 1..5) {
            while (time!! < 20.0) {
                println(time!!)
                Thread.sleep(33)
                time = time?.plus(0.033)
                publishProgress(time)
            }
            time = 0.0
            progressBar.progress = 0
        }
        return null
    }

    override fun onProgressUpdate(vararg values: Double?) {
        for (value in values) {
            if(value!! >20.0) {
                textViewQuestion.text = category.clues!![index].question
                index += 1
            }
            progressBar.progress = (time!! * 5).toInt()
        }
    }

    override fun onPostExecute(result: Void?) {
        //launchaj loeaderboard
    }
}