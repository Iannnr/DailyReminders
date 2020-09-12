package personal.ianroberts.dailyreminder.ui

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.plugins.RxJavaPlugins
import personal.ianroberts.dailyreminder.utils.CrashlyticsService
import personal.ianroberts.dailyreminder.utils.FirebaseService

abstract class BaseActivity : AppCompatActivity() {

    abstract var firebaseTracking: FirebaseService

    abstract var crashlytics: CrashlyticsService

    override fun onResume() {
        super.onResume()

        RxJavaPlugins.setErrorHandler {
            crashlytics.logException(it)
        }
    }
}