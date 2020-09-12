package personal.ianroberts.dailyreminder.notifications

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import personal.ianroberts.dailyreminder.database.NotificationRepo
import personal.ianroberts.database.entities.NotificationItem

class NotificationListViewModel @ViewModelInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    notificationRepo: NotificationRepo
) : ViewModel() {

    val disposable = CompositeDisposable()

    private val _notifications = MutableLiveData<List<NotificationItem>>()
    val notifications: LiveData<List<NotificationItem>>
        get() = _notifications

    init {
        disposable.add(
            notificationRepo
                .allNotifications
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_notifications::postValue, {})
        )
    }


}