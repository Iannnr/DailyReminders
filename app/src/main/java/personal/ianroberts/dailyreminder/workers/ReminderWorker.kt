package personal.ianroberts.dailyreminder.workers

import android.content.Context
import android.util.Log
import androidx.core.os.bundleOf
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.preference.PreferenceManager
import androidx.work.*
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import personal.ianroberts.dailyreminder.BuildConfig
import personal.ianroberts.dailyreminder.Constants
import personal.ianroberts.dailyreminder.Constants.KEY_FREQUENCY
import personal.ianroberts.dailyreminder.Constants.KEY_SILENT
import personal.ianroberts.dailyreminder.R
import personal.ianroberts.dailyreminder.database.NotificationRepo
import personal.ianroberts.dailyreminder.notifications.NotificationFactory
import personal.ianroberts.dailyreminder.utils.FirebaseService
import personal.ianroberts.dailyreminder.utils.orZero
import personal.ianroberts.dailyreminder.utils.toHourMinute
import personal.ianroberts.database.entities.NotificationItem
import java.security.SecureRandom
import java.util.*
import java.util.concurrent.TimeUnit


class ReminderWorker @WorkerInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    val factory: NotificationFactory,
    private val tracker: FirebaseService,
    private val notificationRepo: NotificationRepo
) : RxWorker(context, params) {

    override fun createWork(): Single<Result> {
        Log.i("IanRoberts", "Worker Ran")

        val random = SecureRandom()
        val strings = applicationContext.resources.getStringArray(R.array.reminders)
        val nextReminder = strings[random.nextInt(strings.size)]

        factory.createNotificationChannel(
            applicationContext.getString(R.string.reminders_notification_channel),
            applicationContext.getString(R.string.reminders_notification_description)
        )

        val lastNotifId = factory.preferences.get<Double>(Constants.LAST_ID)?.toInt().orZero()

        val silent = PreferenceManager.getDefaultSharedPreferences(applicationContext).getBoolean(KEY_SILENT, false)


        if (!silent /*&& !App.inForeground*/) {
            tracker.trackEvent(
                "send_notification",
                bundleOf(FirebaseAnalytics.Param.START_DATE to System.currentTimeMillis().toString())
            )

            factory.createAndNotify(
                Constants.NOTIFICATION_CHANNEL,
                applicationContext.getString(R.string.app_name),
                nextReminder,
                lastNotifId + 1
            )
        }

        return notificationRepo.insert(listOf(NotificationItem(lastNotifId + 1L, System.currentTimeMillis(), nextReminder)))
            .subscribeOn(backgroundScheduler)
            .materialize<Unit>()
            .map {
                Result.success()
            }
            .onErrorReturn {
                it.printStackTrace()
                Result.retry()
            }
    }
}

fun enqueueReminders(@ApplicationContext context: Context) {
    val frequency = PreferenceManager.getDefaultSharedPreferences(context)
        .getString(KEY_FREQUENCY, "60")?.toLong() ?: 60

    val startTime = PreferenceManager.getDefaultSharedPreferences(context)
        .getString(Constants.KEY_REMINDER_START, "0")?.toLongOrNull() ?: 0

    val (hour, minute) = startTime.toHourMinute()

    val startCalendar = Calendar.getInstance().also {
        it.add(Calendar.DATE, 1)
        it.set(Calendar.HOUR_OF_DAY, hour)
        it.set(Calendar.MINUTE, minute)
    }

    val offset =
        if (startCalendar.timeInMillis != 0L) {
            startCalendar.timeInMillis - Calendar.getInstance().timeInMillis
        } else {
            1 * (1000 * 60)
        }

    val request = PeriodicWorkRequest.Builder(
        ReminderWorker::class.java,
        frequency,
        TimeUnit.MINUTES
    )
        .addTag("Reminder")
        .setInitialDelay(if (BuildConfig.DEBUG) 5 * 1000 else offset, TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork("Reminder", ExistingPeriodicWorkPolicy.REPLACE, request)

}

