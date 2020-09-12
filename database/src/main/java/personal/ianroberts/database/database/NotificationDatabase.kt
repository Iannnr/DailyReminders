package personal.ianroberts.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import personal.ianroberts.database.dao.NotificationDao
import personal.ianroberts.database.entities.NotificationItem

@Database(entities = [NotificationItem::class], version = 1, exportSchema = false)
abstract class NotificationDatabase : RoomDatabase() {
    abstract fun getNotificationDao(): NotificationDao
}