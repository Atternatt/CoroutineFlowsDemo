package com.m2f.sherpanytest.coreBusiness.domain.features.photos.interactor

import com.m2f.sherpanytest.coreBusiness.arch.data.operation.MainSyncOperation
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.GetRepository
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Photo
import com.m2f.sherpanytest.coreBusiness.domain.features.photos.data.queries.AllPhotosQuery
import javax.inject.Inject

class DefaultFetchAllPhotosInteractor @Inject constructor(private val repository: GetRepository<Photo>): FetchAllPhotosInteractor {
    override suspend fun invoke(): List<Photo> {
        return repository.getAll(AllPhotosQuery, MainSyncOperation)
    }
}