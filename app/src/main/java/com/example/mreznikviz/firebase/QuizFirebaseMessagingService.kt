package com.example.mreznikviz.firebase

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.example.mreznikviz.MainActivity
import com.example.mreznikviz.R
import com.example.mreznikviz.WaitFromNotificationActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class QuizFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        if(MainActivity.getUser()!=null) {
            FirebaseDatabase.getInstance().reference.child("tokens").child(MainActivity.getUser()!!.userName).setValue(
                token
            )
        }
        Log.d(TAG, "Refreshed token: $token")
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("onMessageReceived", "Message received: ${remoteMessage.from}")

        val broadCastIntent = Intent().apply {
            action = ACTION_RESPONSE
            addCategory(Intent.CATEGORY_DEFAULT)
        }
        broadCastIntent.putExtra("message", remoteMessage.data?.get("message"))
        broadCastIntent.putExtra("quizId", remoteMessage.data?.get("quizId"))
        sendBroadcast(broadCastIntent)

        val intent = Intent(this, WaitFromNotificationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(remoteMessage.notification!!.title)
            .setContentText(remoteMessage.notification!!.body)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {
        val ACTION_RESPONSE = "messageReceived"
    }

}
