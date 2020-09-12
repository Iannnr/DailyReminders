package personal.ianroberts.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Single

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg entity: T): Completable

    @Update
    fun update(vararg entity: T): Single<Int>

    @Delete
    fun delete(vararg entity: T): Single<Int>
}