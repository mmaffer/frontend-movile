package com.example.matchpet.core.util

data class ResultState<out T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    companion object {
        fun <T> loading(): ResultState<T> = ResultState(isLoading = true)
        fun <T> success(data: T): ResultState<T> = ResultState(data = data)
        fun <T> error(message: String): ResultState<T> = ResultState(error = message)
    }
}
