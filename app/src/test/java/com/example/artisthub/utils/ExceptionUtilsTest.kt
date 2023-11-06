package com.example.artisthub.utils

import com.example.artisthub.core.utils.network.ExceptionUtils
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException
import retrofit2.Retrofit

class ExceptionUtilsTest {

    val mockHttpResponse = mockk<com.jakewharton.retrofit2.adapter.rxjava2.HttpException>()
    val mockHttpRetrofitResponse = mockk<HttpException>()


    @Test
    fun `handleException should return correct error message for SocketTimeoutException`() {
        val exception = SocketTimeoutException("Test exception")
        val errorMessage = ExceptionUtils.handleException(exception)
        assertEquals("Request time out. Try again", errorMessage)
    }

    @Test
    fun `handleException should return correct error message for UnknownHostException`() {
        val exception = UnknownHostException("Test exception")
        val errorMessage = ExceptionUtils.handleException(exception)
        assertEquals("Check your internet connection", errorMessage)
    }

    @Test
    fun `handleException should return correct error message for HttpException`() {
        val exception = mockHttpResponse
        val errorMessage = ExceptionUtils.handleException(exception)
        assertEquals("Check your internet connection", errorMessage)
    }

    @Test
    fun `handleException should return correct error message for ConnectException`() {
        val exception = ConnectException("Test exception")
        val errorMessage = ExceptionUtils.handleException(exception)
        assertEquals("Check your internet connection", errorMessage)
    }

    @Test
    fun `handleException should return correct error message for retrofit2 HttpException`() {
        val exception = mockHttpRetrofitResponse
        val errorMessage = ExceptionUtils.handleException(exception)
        assertEquals("Bad Request", errorMessage)
    }

    @Test
    fun `handleException should return default error message for other types of exceptions`() {
        val exception = RuntimeException("Test exception")
        val errorMessage = ExceptionUtils.handleException(exception)
        assertEquals("Something went wrong", errorMessage)
    }
}
