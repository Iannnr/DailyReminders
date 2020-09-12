package personal.ianroberts.dailyreminder.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import personal.ianroberts.dailyreminder.R
import personal.ianroberts.dailyreminder.notifications.NotificationListFragment
import personal.ianroberts.dailyreminder.settings.SettingsFragment
import personal.ianroberts.dailyreminder.settings.SettingsViewModel
import personal.ianroberts.dailyreminder.utils.CrashlyticsService
import personal.ianroberts.dailyreminder.utils.FirebaseService
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    val vm: SettingsViewModel by viewModels()

    @Inject
    override lateinit var firebaseTracking: FirebaseService

    @Inject
    override lateinit var crashlytics: CrashlyticsService

    private lateinit var viewPager2: ViewPager2

    val fragments: List<Fragment> by lazy {
        listOf(
            SettingsFragment(),
            NotificationListFragment()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager2 = findViewById(R.id.viewpager)
        viewPager2.adapter = SlidingAdapter(this, fragments)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = when (fragments[position]) {
                is SettingsFragment -> "Settings"
                is NotificationListFragment -> "Notifications"
                else -> null
            }
        }.attach()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (::viewPager2.isInitialized && viewPager2.currentItem > 0) --viewPager2.currentItem else super.onBackPressed()
    }
}