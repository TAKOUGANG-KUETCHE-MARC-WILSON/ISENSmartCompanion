package fr.isen.marcw.isensmartcompanion.model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import fr.isen.marcw.isensmartcompanion.R
import fr.isen.marcw.isensmartcompanion.model.NotificationReceiver.NotificationReceiver

class NotificationWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        val eventId = inputData.getString("eventId") ?: return Result.failure()
        val eventTitle = inputData.getString("eventTitle") ?: "Événement"
        val eventDate = inputData.getString("eventDate") ?: ""

        sendNotification(eventId, eventTitle, eventDate)

        return Result.success()
    }

    private fun sendNotification(eventId: String, title: String, date: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "event_notifications",
                "Notifications d'événements",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(applicationContext, "event_notifications")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Rappel événement")
            .setContentText("$title prévu le $date")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(eventId.hashCode(), builder.build())
    }
}

