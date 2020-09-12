package personal.ianroberts.dailyreminder.utils

import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

class CrashlyticsService @Inject constructor() {

    fun logException(e: Throwable) {
        FirebaseCrashlytics.getInstance().log(e.localizedMessage ?: e.message ?: e.stackTrace.joinToString(","))
    }
}