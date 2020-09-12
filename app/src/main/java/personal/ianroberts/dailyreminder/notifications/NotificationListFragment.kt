package personal.ianroberts.dailyreminder.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import personal.ianroberts.dailyreminder.R


@AndroidEntryPoint
class NotificationListFragment : Fragment() {

    private val vm: NotificationListViewModel by viewModels()
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notification_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        val adapter = NotificationListAdapter(AsyncDifferConfig.Builder(NotificationItemDiff()).build())
        recyclerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(
            context,
            (recyclerView.layoutManager as LinearLayoutManager).orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        vm.notifications.observe(viewLifecycleOwner, Observer {
            Log.i("IanRoberts", "${it.size}")
            adapter.updateData(it, {})
            adapter.notifyDataSetChanged()
        })
    }
}