package com.example.mpcb.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.mpcb.R

/*
*   This file contains extension function of Notification Manager, which will help
*   sending notification
*/


private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

lateinit var notificationManager: NotificationManager

/**
 * Builds and delivers a notification.
 *
 * @param messageBody, notification text.
 * @param context, activity context.
 */
fun NotificationManager.sendNotification(
    messageTitle: String = "",
    messageBody : String = "",
    applicationContext: Context
){


    // Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        //TODO 26/12/19 Change this channel ID once its confirmed
        applicationContext.getString(R.string.channel_id_1)
    )
        .setSmallIcon(R.drawable.mpcb_logo)
        //TODO 26/12/19 Change this Content title once its confirmed
        .setContentTitle(messageTitle)
        .setContentText(messageBody)


    notify(NOTIFICATION_ID, builder.build())
}

/**
 * Create channel id for Android O & above
 */
fun createChannel(context: Context, channelId: String, channelName: String) {

    /*
     * If the device is O & above, then create a channel
     * else create normal notification manager.
     */

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
//            notificationChannel.description = "Time for breakfast"


        //Create Notification Manager
        notificationManager = context.getSystemService(
            NotificationManager::class.java
        )
        //Add channel to Notification Manager
        notificationManager.createNotificationChannel(notificationChannel)

    } else {

        notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager
    }
}
