package com.example.mreznikviz.net

import android.os.AsyncTask
import com.example.mreznikviz.entities.JsonCategory
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class QuzzFetcher : AsyncTask<Long?, Void, JSONObject?>() {


    override fun doInBackground(vararg params: Long?): JSONObject? {
        var url: URL = URL("https://jservice.io/api/category?id=770")
        val httpConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        val inputstream: InputStream = httpConnection.inputStream
        val bufferedReader: BufferedReader = BufferedReader(InputStreamReader(inputstream))
        var line: String = ""
        var data: String = ""
        while(line!=null){
            line = bufferedReader.readLine()
            data = data + line
        }

        val jsonArray : JSONArray = JSONArray(data)
        var jsonObj?: JSONObject
        for(i in 0..jsonArray.length()){
            jsonObj = jsonArray.get(i) as JSONObject
        }
        return jsonObj
    }

    override fun onPostExecute(result: JSONObject?) {
        print(result?.get("title"))
    }
}