package com.example.artisthub.core.utils.network


sealed class NetworkResult<T>(val data: T? = null, val errorMessage: String? = null, val exception: Throwable? = null) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Failed<T>(data: T?): NetworkResult<T>(data)
    class Error<T>(exception: Throwable): NetworkResult<T>(null, null,exception)
}
