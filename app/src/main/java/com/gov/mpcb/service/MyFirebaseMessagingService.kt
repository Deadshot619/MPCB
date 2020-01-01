package com.gov.mpcb.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.gov.mpcb.utils.notificationManager
import com.gov.mpcb.utils.sendNotification

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
