package com.example.mpcb.utils

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.mpcb.R

/*
*   This file contains extension function of Notification Manager, which will help
*   sending notification
*/


private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

/**
 * Builds and delivers a notification.
 *
 * @param messageBody, notification text.
 * @param context, activity context.
 */
fun NotificationManager.sendNotification(messageBody : String, applicationContext: Context){


    // Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        //TODO 26/12/19 Change this channel ID once its confirmed
        "channel_id"
    )
        .setSmallIcon(R.drawable.ic_lock_icon)
        //TODO 26/12/19 Change this Content title once its confirmed
        .setContentTitle("Content Title")
        .setContentText(messageBody)



    notify(NOTIFICATION_ID, builder.build())
}