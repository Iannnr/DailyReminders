package personal.ianroberts.database

import io.reactivex.Completable
import io.reactivex.Single
import personal.ianroberts.database.dao.BaseDao

abstract class BaseRepo<T>(private val dao: BaseDao<T>) {

    fun insert(item: T) = dao.insert(item)
    fun update(item: T) = dao.insert(item)
    fun delete(item: T) = dao.insert(item)

    abstract fun insert(item: List<T>): Completable
    abstract fun update(item: List<T>): Single<Int>
    abstract fun delete(item: List<T>): Single<Int>
}