package personal.ianroberts.dailyreminder.utils

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class FirebaseService @Inject constructor(private val firebaseAnalytics: FirebaseAnalytics) {

    fun trackEvent(eventName: String, extraParams: Bundle = Bundle()) {
        firebaseAnalytics.logEvent(eventName, extraParams)
    }
}