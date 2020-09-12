package personal.ianroberts.dailyreminder.modules

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import personal.ianroberts.dailyreminder.notifications.NotificationFactory
import personal.ianroberts.dailyreminder.notifications.NotificationFactoryImpl
import personal.ianroberts.dailyreminder.preferences.PreferenceManager
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NotificationModule {

    @Singleton
    @Provides
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManagerCompat {
        return NotificationManagerCompat.from(context)
    }

    @Provides
    fun provideNotificationFactory(
        @ApplicationContext context: Context,
        manager: NotificationManagerCompat,
        prefs: PreferenceManager
    ): NotificationFactory {
        return NotificationFactoryImpl(
            manager,
            context,
            prefs
        )
    }
}