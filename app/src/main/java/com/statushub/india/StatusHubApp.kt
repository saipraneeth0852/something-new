package com.statushub.india

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

import com.statushub.india.worker.DailyStatusWorker

@HiltAndroidApp
class StatusHubApp : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        DailyStatusWorker.schedule(this)
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val name = "Daily Status"
            val descriptionText = "Get daily morning status updates"
            val importance = android.app.NotificationManager.IMPORTANCE_DEFAULT
            val channel = android.app.NotificationChannel("daily_status_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(android.content.Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
