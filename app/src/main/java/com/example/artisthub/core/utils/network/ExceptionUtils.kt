package com.example.artisthub.core.utils.network

import androidx.lifecycle.MutableLiveData
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ExceptionUtils {
    fun handleException(exception: Throwable):String {
        println(exception)
        return  when (exception) {
            is SocketTimeoutException -> "Request time out. Try again"
            is UnknownHostException -> "Check your internet connection"
            is HttpException -> "Check your internet connection"
            is ConnectException -> "Check your internet connection"
            is retrofit2.HttpException -> "Bad Request"
            else -> "Something went wrong"
        }
    }
}
fun Throwable.handleException():String{
    return ExceptionUtils.handleException(this)
}
