package personal.ianroberts.dailyreminder.notifications

import personal.ianroberts.dailyreminder.preferences.PreferenceManager

interface NotificationFactory {
    fun createNotificationChannel(channelName: String, description: String)
    fun createAndNotify(channel: String, title: String, preview: String, lastNotifId: Int)

    val preferences: PreferenceManager
}