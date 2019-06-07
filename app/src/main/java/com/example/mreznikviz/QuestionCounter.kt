package com.example.mreznikviz

import android.os.AsyncTask
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.mreznikviz.animations.BounceAnimation
import com.example.mreznikviz.entities.JsonCategory

public class QuestionCounter(val progressBar: ProgressBar, val textViewQuestion: TextView, val category: JsonCategory, val button: RelativeLayout): AsyncTask<Void?, Double?, Void?>(){
    var time: Double? = null
    var index: Int = 1

    override fun onPreExecute() {
        textViewQuestion.text =  category.clues!![0].question
        val bounceAnimation = BounceAnimation(button)
        bounceAnimation.addOnFinishListener{time = 20.0}.enableOnTouchDemand()
    }

    override fun doInBackground(vararg params: Void?): Void? {
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