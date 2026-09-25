package com.horizon.lifeos.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
Id,
                "LifeOS Focus Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("حالت تمرکز فعال است")
            .setContentText("LifeOS در حال مدیریت زمان تمرکز شماست.")
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()

        startForeground(1001, notification)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
