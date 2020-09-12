package personal.ianroberts.dailyreminder.database

import personal.ianroberts.database.BaseRepo
import personal.ianroberts.database.dao.NotificationDao
import personal.ianroberts.database.entities.NotificationItem
import javax.inject.Inject

class NotificationRepo @Inject constructor(private val dao: NotificationDao) : BaseRepo<NotificationItem>(dao) {

    override fun insert(item: List<NotificationItem>) = dao.insert(*item.toTypedArray())
    override fun update(item: List<NotificationItem>) = dao.delete(*item.toTypedArray())
    override fun delete(item: List<NotificationItem>) = dao.delete(*item.toTypedArray())

    val allNotifications = dao.getAllNotifications()
}