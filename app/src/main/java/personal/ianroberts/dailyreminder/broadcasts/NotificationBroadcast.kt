package personal.ianroberts.dailyreminder.broadcasts

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.CallSuper
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.schedulers.Schedulers
import personal.ianroberts.dailyreminder.database.NotificationRepo
import personal.ianroberts.database.entities.NotificationItem
import javax.inject.Inject

abstract class HiltBroadcastReceiver : BroadcastReceiver() {
    @CallSuper
    override fun onReceive(context: Context, intent: Intent) {
    }
}

@AndroidEntryPoint
class NotificationBroadcast : HiltBroadcastReceiver() {

    @Inject
    lateinit var repo: NotificationRepo

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        if (intent.action != "mark as read") return

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notifId = intent.extras?.getInt("notificationId")!!

        repo.getNotification(notifId)
            .subscribeOn(Schedulers.io())
            .map {
                it.copy(read = true)
            }
            .doOnSuccess {
                repo.insert(listOf(it as NotificationItem)).subscribe({}, {})
            }
            .subscribe({}, {
                it.printStackTrace()
            })

        manager.cancel(notifId)
    }
}