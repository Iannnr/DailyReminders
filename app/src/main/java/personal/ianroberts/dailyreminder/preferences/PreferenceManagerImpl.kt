package personal.ianroberts.dailyreminder.preferences

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import personal.ianroberts.dailyreminder.Constants
import personal.ianroberts.dailyreminder.utils.fromJson
import javax.inject.Inject

class PreferenceManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) : PreferenceManager {

    val prefs = context.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE)

    override operator fun <T : Any> get(key: String): T? {
        val prefsValue = prefs.getString(key, null) ?: return null

        return gson.fromJson<Any>(prefsValue) as T
    }

    override fun <T : Any> set(key: String, value: T) {
        prefs.edit {
            putString(key, gson.toJson(value))
        }
    }
}