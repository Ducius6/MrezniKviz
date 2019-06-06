package com.example.mreznikviz.net

import android.os.AsyncTask
import android.util.Log
import com.example.mreznikviz.entities.JsonCategory

public class QuizzFetcher : AsyncTask<Long?, Void, JsonCategory?>() {


    override fun doInBackground(vararg params: Long?): JsonCategory? {
        var rest = RestFactory.instance
        var quizzQuestionList: JsonCategory? = null
        for (param in params){
            quizzQuestionList = rest.getQuizzQuestions(param)
        }
        return quizzQuestionList
    }

    override fun onPostExecute(result: JsonCategory?) {
        print("TITLE" + result?.title)
        Log.d("TITLE", result?.title)
    }
}