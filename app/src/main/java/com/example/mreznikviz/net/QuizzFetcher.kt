package com.example.mreznikviz.net

import android.os.AsyncTask
import com.example.mreznikviz.entities.JsonCategory

class QuzzFetcher : AsyncTask<Long?, Void, JsonCategory?>() {


    override fun doInBackground(vararg params: Long?): JsonCategory? {
        var rest = RestFactory.instance
        var quizzQuestionList: JsonCategory? = null
        for (param in params){
            quizzQuestionList = rest.getQuizzQuestions(param)
        }
        return quizzQuestionList
    }

    override fun onPostExecute(result: JsonCategory?) {
        print(result?.title)
    }
}