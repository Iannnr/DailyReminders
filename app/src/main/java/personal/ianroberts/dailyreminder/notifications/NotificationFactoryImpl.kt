package personal.ianroberts.dailyreminder.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toIcon
import dagger.hilt.android.qualifiers.ApplicationContext
import personal.ianroberts.dailyreminder.Constants
import personal.ianroberts.dailyreminder.R
import personal.ianroberts.dailyreminder.broadcasts.NotificationBroadcast
import personal.ianroberts.dailyreminder.preferences.PreferenceManager
import javax.inject.Inject


class NotificationFactoryImpl @Inject constructor(
    private val notificationManager: NotificationManagerCompat,
    @ApplicationContext private val context: Context,
    override val preferences: PreferenceManager
) : NotificationFactory {

    override fun createNotificationChannel(channelName: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel =
                NotificationChannel(Constants.NOTIFICATION_CHANNEL, channelName, importance).apply {
                    this.description = description
                    enableVibration(true)
                    enableLights(true)
                    vibrationPattern = longArrayOf(100, 100, 100)
                }
            // Register the channel with the system

            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun createAndNotify(channel: String, title: String, preview: String, lastNotifId: Int) {
        val emptyIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val bm = ContextCompat.getDrawable(context, R.mipmap.ic_launcher_new)?.toBitmap()

        val actionIntent = Intent(context, NotificationBroadcast::class.java).apply {
            putExtra("notificationId", lastNotifId)
            action = "mark as read"
        }

        val actionPendingIntent =
            PendingIntent.getBroadcast(context, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, channel)
            .setSmallIcon(R.mipmap.ic_launcher_new)
            .setLargeIcon(bm)
            .setContentTitle(title)
            .setContentText(preview)
            .setContentIntent(emptyIntent)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(preview)
            )
            .addAction(
                NotificationCompat.Action(
                    IconCompat.createFromIcon(context, bm?.toIcon()!!),
                    context.getString(R.string.mark_as_read),
                    actionPendingIntent
                )
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notification = builder.build()
        notification.flags = NotificationCompat.FLAG_AUTO_CANCEL

        with(NotificationManagerCompat.from(context)) {
            preferences.set(Constants.LAST_ID, lastNotifId)
            notify(this::class.simpleName, lastNotifId, notification)
        }
    }
}