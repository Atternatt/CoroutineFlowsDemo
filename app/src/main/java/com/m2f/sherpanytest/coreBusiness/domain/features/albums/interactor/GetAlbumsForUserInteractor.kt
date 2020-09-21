package com.m2f.sherpanytest.coreBusiness.domain.features.albums.interactor

import com.m2f.sherpanytest.coreBusiness.common.model.domain.Album
import kotlinx.coroutines.flow.Flow


interface GetAlbumsForUserInteractor{

    operator fun invoke(user: Long): Flow<List<Album>>
}