package com.example.mreznikviz.net

import android.os.AsyncTask
import com.example.mreznikviz.entities.Quizz

class QuzzFetcher : AsyncTask<Long?, Void, Quizz?>() {


    override fun doInBackground(vararg params: Long?): Quizz? {
        var rest = RestFactory.instance
        var quizzQuestionList: Quizz? = null
        for (param in params){
            quizzQuestionList = rest.getQuizzQuestions(param)
        }
        return quizzQuestionList
    }

    override fun onPostExecute(result: Quizz?) {
        
    }
}