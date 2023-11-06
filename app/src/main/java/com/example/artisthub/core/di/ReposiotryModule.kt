package com.example.artisthub.core.di

import com.example.artisthub.features.artistdetail.data.ArtistAlbumRepositoryImpl
import com.example.artisthub.features.artistdetail.repository.ArtistAlbumRepository
import com.example.artisthub.features.searchartist.data.ArtistRepositoryImpl
import com.example.artisthub.features.searchartist.repository.ArtistRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ArtistRepository> { ArtistRepositoryImpl(artistAPI = get(), artistNetworkCall = get()) }
    single<ArtistAlbumRepository> { ArtistAlbumRepositoryImpl(artisAlbumtAPI = get(), artistAlbumNetworkCall = get()) }
}