package com.example.artisthub.features.artistdetail.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artisthub.features.artistdetail.data.model.album.GetAlbumsQuery
import com.example.artisthub.features.artistdetail.data.model.album.response.GetAlbumsResponse
import com.example.artisthub.features.artistdetail.repository.ArtistAlbumRepository
import com.example.artisthub.core.utils.view.SingleLiveEvent
import com.example.artisthub.core.utils.view.ViewModelUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistAlbumViewModel(private val artistAlbumRepository: ArtistAlbumRepository): ViewModel() {
    private val _getAlbumsResponse = MutableLiveData<GetAlbumsResponse>()
    val getAlbumsResponse: LiveData<GetAlbumsResponse> = _getAlbumsResponse

   val showError = SingleLiveEvent<String>()

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading

    fun getAlbums(query: GetAlbumsQuery){
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO){
                artistAlbumRepository.getAlbums(query)
            }
            ViewModelUtil.validateNetworkResponse(response, {it.error ?: ""}, _getAlbumsResponse,showError, _showLoading)
        }
    }
}