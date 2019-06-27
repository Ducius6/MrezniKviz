package com.example.mreznikviz.firebase

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import com.example.mreznikviz.MainActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class QuizFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        FirebaseDatabase.getInstance().reference.child("tokens").child(MainActivity.getUser().userName).setValue(
            token)
        Log.d(TAG, "Refreshed token: $token")
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("onMessageReceived", "Message received: ${remoteMessage.from}")

        val broadCastIntent = Intent().apply {
            action = ACTION_RESPONSE
            addCategory(Intent.CATEGORY_DEFAULT)
        }
        broadCastIntent.putExtra("message", remoteMessage.data?.get("message"))
        sendBroadcast(broadCastIntent)
    }

    companion object {
        val ACTION_RESPONSE = "messageReceived"
    }

}
