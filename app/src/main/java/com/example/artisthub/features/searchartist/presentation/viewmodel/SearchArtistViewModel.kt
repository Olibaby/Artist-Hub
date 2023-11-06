package com.example.artisthub.features.searchartist.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artisthub.features.searchartist.data.model.SearchArtistQuery
import com.example.artisthub.features.searchartist.data.model.response.SearchArtistResponse
import com.example.artisthub.features.searchartist.repository.ArtistRepository
import com.example.artisthub.core.utils.view.SingleLiveEvent
import com.example.artisthub.core.utils.view.ViewModelUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchArtistViewModel(private val artistRepository: ArtistRepository): ViewModel() {
    private val _searchArtistResponse = MutableLiveData<SearchArtistResponse>()
    val searchArtistResponse: LiveData<SearchArtistResponse> = _searchArtistResponse

    val showError = SingleLiveEvent<String>()

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading

    fun searchArtist(query: SearchArtistQuery){
        _showLoading.value = true
        viewModelScope.launch {
           val response = withContext(Dispatchers.IO){
               artistRepository.searchArtist(query)
           }
            ViewModelUtil.validateNetworkResponse(response, {it.error ?: ""}, _searchArtistResponse, showError, _showLoading)
        }
    }
}