package personal.ianroberts.dailyreminder.preferences

interface PreferenceManager {
    fun <T : Any> get(key: String): T?
    fun <T : Any> set(key: String, value: T)
}