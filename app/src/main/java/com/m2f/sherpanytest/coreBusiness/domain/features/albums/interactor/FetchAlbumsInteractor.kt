package com.m2f.sherpanytest.coreBusiness.domain.features.albums.interactor

import com.m2f.sherpanytest.coreBusiness.common.model.domain.Album
import kotlinx.coroutines.flow.Flow


interface FetchAlbumsInteractor {

    operator fun invoke(): Flow<List<Album>>
}