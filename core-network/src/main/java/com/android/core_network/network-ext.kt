package com.android.core_network

import com.android.core_model.Resource

suspend fun <T> requestData(apiCall: suspend () -> T): Resource<T> {
    return try {
        val response = apiCall.invoke()
        if (response != null)
            Resource.Success(response)
        else
            Resource.Error("Ups something happened")
    } catch (throwable: Throwable) {
        Resource.Error(throwable.message ?: "")
    }
}
