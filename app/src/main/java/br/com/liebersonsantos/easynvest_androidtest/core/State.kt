package br.com.liebersonsantos.easynvest_androidtest.core

/**
 * Created by lieberson on 6/29/21.
 * @author lieberson.xsantos@gmail.com
 */
data class State<out T>(
    val status: Status,
    val loading: Boolean?,
    val data: T?,
    val error: Throwable?
) {
    companion object {
        fun <T> success(data: T?): State<T> {
            return State(Status.SUCCESS, loading = false, data = data, error = null)
        }

        fun <T> error(error: Throwable): State<T> {
            return State(Status.ERROR, loading = false, data = null, error = error)
        }

        fun <T> loading(loading: Boolean): State<T> {
            return State(Status.LOADING, loading = loading, data = null, error = null)
        }
    }
}