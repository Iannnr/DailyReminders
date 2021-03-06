package personal.ianroberts.dailyreminder.modules

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object ConverterModule {

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }
}