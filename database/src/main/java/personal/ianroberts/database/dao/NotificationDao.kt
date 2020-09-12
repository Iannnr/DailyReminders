package personal.ianroberts.database.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import personal.ianroberts.database.entities.NotificationItem

@Dao
abstract class NotificationDao : BaseDao<NotificationItem> {

    @Query("SELECT * FROM notification")
    abstract fun getAllNotifications(): Flowable<List<NotificationItem>>
}