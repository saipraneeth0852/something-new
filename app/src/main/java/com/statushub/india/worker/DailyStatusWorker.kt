package com.statushub.india.worker

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.statushub.india.MainActivity
import com.statushub.india.R
import java.util.concurrent.TimeUnit

class DailyStatusWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        showNotification()
        return Result.success()
    }

    private fun showNotification() {
        // Check notification permission for Android 13+
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!hasPermission) {
                // Don't attempt to show notification without permission
                return
            }
        }

        // Create intent to open the app when notification is tapped
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(applicationContext, "daily_status_channel")
            .setSmallIcon(R.drawable.ic_notification) // Use a proper notification icon
            .setContentTitle("Good Morning! ☀️")
            .setContentText("Your daily status update is ready. Tap to share now!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(1, builder.build())
        }
    }

    companion object {
        fun schedule(context: Context) {
            val workRequest = PeriodicWorkRequestBuilder<DailyStatusWorker>(24, TimeUnit.HOURS)
                .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "daily_status_work",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
        }

        /**
         * Calculate delay so notification fires at 8:00 AM daily.
         */
        private fun calculateInitialDelay(): Long {
            val calendar = java.util.Calendar.getInstance().apply {
                set(java.util.Calendar.HOUR_OF_DAY, 8)
                set(java.util.Calendar.MINUTE, 0)
                set(java.util.Calendar.SECOND, 0)
                set(java.util.Calendar.MILLISECOND, 0)
                // If 8 AM has already passed today, schedule for tomorrow
                if (before(java.util.Calendar.getInstance())) {
                    add(java.util.Calendar.DAY_OF_YEAR, 1)
                }
            }
            return calendar.timeInMillis - System.currentTimeMillis()
        }
    }
}
