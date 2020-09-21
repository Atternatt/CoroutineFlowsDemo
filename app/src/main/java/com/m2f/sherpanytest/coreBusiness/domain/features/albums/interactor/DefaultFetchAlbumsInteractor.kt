package com.m2f.sherpanytest.coreBusiness.domain.features.albums.interactor

import com.m2f.sherpanytest.coreBusiness.arch.data.operation.MainSyncOperation
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.flow.FlowGetRepository
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Album
import com.m2f.sherpanytest.coreBusiness.domain.features.albums.data.queries.AllAlbumsQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultFetchAlbumsInteractor @Inject constructor(
    private val repository: FlowGetRepository<Album>
) : FetchAlbumsInteractor {

    override operator fun invoke(): Flow<List<Album>> {
        return repository.getAll(AllAlbumsQuery, MainSyncOperation)
    }
}