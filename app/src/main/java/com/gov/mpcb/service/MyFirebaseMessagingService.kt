package com.gov.mpcb.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.gov.mpcb.utils.constants.Constants.Companion.FIREBASE_TOKEN
import com.gov.mpcb.utils.notificationManager
import com.gov.mpcb.utils.sendNotification
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper.setStringPreference

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
        //Save new firebase token in Shared Pref
        setStringPreference(FIREBASE_TOKEN, p0)
    }

}
