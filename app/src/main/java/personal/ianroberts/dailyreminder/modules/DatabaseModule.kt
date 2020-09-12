package personal.ianroberts.dailyreminder.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import personal.ianroberts.database.dao.NotificationDao
import personal.ianroberts.database.database.NotificationDatabase
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNotificationDb(@ApplicationContext context: Context): NotificationDatabase {
        return Room.databaseBuilder(context, NotificationDatabase::class.java, "notifications")
            .build()
    }

    @Provides
    @Singleton
    fun provideNotificationDao(db: NotificationDatabase): NotificationDao {
        return db.getNotificationDao()
    }
}