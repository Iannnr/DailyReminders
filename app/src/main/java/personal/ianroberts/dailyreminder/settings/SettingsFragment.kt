package personal.ianroberts.dailyreminder.settings

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TimePicker
import androidx.core.content.edit
import androidx.fragment.app.activityViewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import dagger.hilt.android.AndroidEntryPoint
import personal.ianroberts.dailyreminder.Constants
import personal.ianroberts.dailyreminder.R
import personal.ianroberts.dailyreminder.preferences.DatePreference
import personal.ianroberts.dailyreminder.utils.toHourMinute
import personal.ianroberts.dailyreminder.workers.enqueueReminders
import java.util.*

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat(), TimePickerDialog.OnTimeSetListener {

    val vm: SettingsViewModel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.root_preferences)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = findPreference<DatePreference>(Constants.KEY_REMINDER_START)
        pref?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val time = it.sharedPreferences.getString(it.key!!, "0")

            time?.toLongOrNull()?.let {
                val (hour, minute) = it.toHourMinute()
                TimePickerDialog(requireContext(), this, hour, minute, true).show()
            }

            true
        }
    }

    override fun onDisplayPreferenceDialog(preference: Preference) {
        if (preference !is DatePreference) {
            super.onDisplayPreferenceDialog(preference)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        enqueueReminders(requireContext())
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Log.i("IanRoberts", "${hourOfDay}, ${minute}")
        val time = Calendar.getInstance(Locale.getDefault()).also {
            it.set(Calendar.HOUR_OF_DAY, hourOfDay)
            it.set(Calendar.MINUTE, minute)
        }

        preferenceManager.sharedPreferences.edit(true) {
            putString(Constants.KEY_REMINDER_START, time.timeInMillis.toString())
            putInt(Constants.KEY_REMINDER_START_HOUR, hourOfDay)
            putInt(Constants.KEY_REMINDER_START_MINUTE, minute)
        }

        enqueueReminders(requireContext())
    }

}