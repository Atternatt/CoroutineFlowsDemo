package com.m2f.sherpanytest.coreBusiness.domain.features.albums.interactor

import com.m2f.sherpanytest.coreBusiness.arch.data.operation.CacheOperation
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.flow.FlowGetRepository
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Album
import com.m2f.sherpanytest.coreBusiness.domain.features.albums.data.queries.AlbumsQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultGetAlbumsForUserInteractor @Inject constructor(private val repository: FlowGetRepository<Album>) : GetAlbumsForUserInteractor {

    override fun invoke(user: Long): Flow<List<Album>> {
        return repository.getAll(AlbumsQuery(user), CacheOperation)
    }
}