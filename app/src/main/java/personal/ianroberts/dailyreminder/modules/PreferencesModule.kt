package personal.ianroberts.dailyreminder.modules

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import personal.ianroberts.dailyreminder.Constants
import personal.ianroberts.dailyreminder.preferences.PreferenceManager
import personal.ianroberts.dailyreminder.preferences.PreferenceManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object PreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context, gson: Gson): PreferenceManager {
        return PreferenceManagerImpl(context, gson)
    }
}