package personal.ianroberts.dailyreminder.notifications

import androidx.recyclerview.widget.DiffUtil
import personal.ianroberts.database.entities.NotificationItem

class NotificationItemDiff : DiffUtil.ItemCallback<NotificationItem>() {

    override fun areItemsTheSame(oldItem: NotificationItem, newItem: NotificationItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: NotificationItem, newItem: NotificationItem): Boolean {
        return oldItem == newItem
    }
}