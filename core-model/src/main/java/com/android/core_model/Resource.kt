package com.android.core_model

/** Based in https://www.geeksforgeeks.org/how-to-handle-api-responses-success-error-in-android/ **/
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data = data)

    class Error<T>(errorMessage: String?) : Resource<T>(message = errorMessage)

    class Loading<T> : Resource<T>()
}