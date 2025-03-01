package fr.isen.marcw.isensmartcompanion.model.NotificationReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Action de notification reçue", Toast.LENGTH_SHORT).show()
    }

}