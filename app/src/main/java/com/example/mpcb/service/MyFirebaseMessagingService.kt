package com.example.mpcb.service

import android.util.Log
import com.example.mpcb.utils.notificationManager
import com.example.mpcb.utils.sendNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService(){

    override fun onMessageReceived(p0: RemoteMessage) {

        p0.notification?.let {
            Log.d("Notification", "Message Notification Body: ${it.body}")
            //Message Services handle notification

            notificationManager.sendNotification(
                messageTitle = p0.notification?.title.toString(),
                messageBody = p0.notification?.body.toString(),
                applicationContext = applicationContext
            )
        }
    }

    override fun onNewToken(p0: String) {

    }

}
