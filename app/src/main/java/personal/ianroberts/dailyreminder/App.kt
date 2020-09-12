package personal.ianroberts.dailyreminder

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    companion object {
        var inForeground = true
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {

        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(workerFactory)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        WorkManager.initialize(this, workManagerConfiguration)

        FirebaseAnalytics.getInstance(this)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
                inForeground = false
            }

            override fun onActivityStarted(activity: Activity) {
                inForeground = true
            }

            override fun onActivityDestroyed(activity: Activity) {
                inForeground = false
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityStopped(activity: Activity) {
                inForeground = false
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                inForeground = true
            }

            override fun onActivityResumed(activity: Activity) {
                inForeground = true
            }

        })
    }
}