package personal.ianroberts.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification")
data class NotificationItem(
    @PrimaryKey(autoGenerate = false) val id: Long,
    val time: Long,
    val content: String,
    val read: Boolean = false,
    val notificationId: Int
)