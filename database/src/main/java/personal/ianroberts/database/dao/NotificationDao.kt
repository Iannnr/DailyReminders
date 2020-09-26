package personal.ianroberts.database.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Maybe
import personal.ianroberts.database.entities.NotificationItem

@Dao
abstract class NotificationDao : BaseDao<NotificationItem> {

    @Query("SELECT * FROM notification")
    abstract fun getAllNotifications(): Flowable<List<NotificationItem>>

    @Query("SELECT * FROM notification where notificationId = :notificationId LIMIT 1")
    abstract fun getNotification(notificationId: Int): Maybe<NotificationItem>
}