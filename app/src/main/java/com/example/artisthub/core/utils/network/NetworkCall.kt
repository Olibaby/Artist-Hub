package com.example.artisthub.core.utils.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import retrofit2.Call
import kotlin.coroutines.CoroutineContext

interface NetworkCall {
   suspend fun <T> makeNetworkCall(query: String, apiCall: suspend (String) -> T, checkIfSuccessful:(T)->Boolean): NetworkResult<T>
}

class NetworkCallImpl: NetworkCall{
    override suspend fun <T> makeNetworkCall(query: String, apiCall: suspend (String) -> T, checkIfSuccessful:(T)->Boolean): NetworkResult<T> {
        return try{
            val response = apiCall.invoke(query)
            if (checkIfSuccessful(response)){
                    return NetworkResult.Success(response)
            } else{
                NetworkResult.Failed(response)
            }
        } catch (ex: Exception){
            println(ex)
            NetworkResult.Error(ex)
        }
    }

}