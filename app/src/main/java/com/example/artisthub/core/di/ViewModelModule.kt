package com.example.artisthub.core.di.module

import com.example.artisthub.features.artistdetail.presentation.viewmodel.ArtistAlbumViewModel
import com.example.artisthub.features.artistdetail.repository.ArtistAlbumRepository
import com.example.artisthub.features.searchartist.presentation.viewmodel.SearchArtistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{ SearchArtistViewModel(artistRepository = get()) }
    viewModel{ ArtistAlbumViewModel(artistAlbumRepository = get()) }
}