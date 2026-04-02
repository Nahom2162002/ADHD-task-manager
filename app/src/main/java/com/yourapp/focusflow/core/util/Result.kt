package com.yourapp.focusflow.core.util

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Failure(val throwable: Throwable) : Result<Nothing>
}
