package personal.ianroberts.dailyreminder.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

inline fun <reified T> Gson.fromJson(json: String) =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)

fun Int?.orZero() = this ?: 0

fun Long.toCalendarHour() =
    Calendar.getInstance(Locale.getDefault()).also { it.timeInMillis = this }.get(Calendar.HOUR_OF_DAY)

fun Long.toCalendarMinute() =
    Calendar.getInstance(Locale.getDefault()).also { it.timeInMillis = this }.get(Calendar.MINUTE)

fun Long.toHourMinute() =
    Pair(
        toCalendarHour(),
        toCalendarMinute()
    )