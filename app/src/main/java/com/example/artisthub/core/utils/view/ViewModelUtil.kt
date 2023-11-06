package com.example.artisthub.core.utils.view

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.artisthub.core.utils.network.NetworkResult
import com.example.artisthub.core.utils.network.handleException

object ViewModelUtil {
    fun <T> validateNetworkResponse(response: NetworkResult<T>, getError:(response:T) -> String,responseObserver: MutableLiveData<T>, errorObserver: MutableLiveData<String>, showLoading: MutableLiveData<Boolean>){
        showLoading.value = false
        when(response){
            is NetworkResult.Success -> responseObserver.value = response.data
            is NetworkResult.Failed -> errorObserver.value = response.data?.let { getError(it) }
            is NetworkResult.Error -> errorObserver.value = response.exception?.handleException()
        }
    }
}

fun <T> LiveData<T>.observeChange(owner: LifecycleOwner, action:(T)->Unit){
    this.observe(owner, Observer { action(it) })
}