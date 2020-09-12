package personal.ianroberts.dailyreminder.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

object BindingUtils {

    @JvmStatic
    @BindingAdapter("app:setDate")
    fun setDate(view: TextView, time: Long) {
        val instant = Instant.ofEpochMilli(time)

        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            .withLocale(Locale.UK)
            .withZone(ZoneId.systemDefault())

        view.text = formatter.format(instant)
    }
}