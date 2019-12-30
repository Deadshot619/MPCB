package com.example.mpcb.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService(){


    override fun onMessageReceived(p0: RemoteMessage) {

        p0.notification?.let {
            Log.d("Notification", "Message Notification Body: ${it.body}")
            //Message Services handle notification

//            Toast.makeText(this, "${it.body}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onNewToken(p0: String) {

    }

}
