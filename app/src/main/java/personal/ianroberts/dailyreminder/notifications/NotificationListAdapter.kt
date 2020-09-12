package personal.ianroberts.dailyreminder.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import personal.ianroberts.dailyreminder.databinding.RowNotificationBinding
import personal.ianroberts.database.entities.NotificationItem

class NotificationListAdapter(diff: AsyncDifferConfig<NotificationItem>) : RecyclerView.Adapter<NotificationViewHolder>() {

    var mDiffer = AsyncListDiffer(AdapterListUpdateCallback(this), diff)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(RowNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        mDiffer.currentList[position]?.let { holder.bind(it) }
    }

    override fun getItemId(position: Int): Long {
        return mDiffer.currentList[position]?.id.hashCode().toLong()
    }

    fun updateData(data: List<NotificationItem>, onSubmit: () -> Unit) {
        mDiffer.submitList(data, onSubmit)
    }
}

class NotificationViewHolder(private val binding: RowNotificationBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: NotificationItem) {
        binding.item = item
        binding.executePendingBindings()
    }
}