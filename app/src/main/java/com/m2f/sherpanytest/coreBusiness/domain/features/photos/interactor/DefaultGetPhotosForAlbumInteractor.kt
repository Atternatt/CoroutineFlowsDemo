package com.m2f.sherpanytest.coreBusiness.domain.features.photos.interactor

import com.m2f.sherpanytest.coreBusiness.arch.data.operation.CacheOperation
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.GetRepository
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Photo
import com.m2f.sherpanytest.coreBusiness.domain.features.photos.data.queries.PhotosForAlbumQuery
import javax.inject.Inject

class DefaultGetPhotosForAlbumInteractor @Inject constructor(private val repository: GetRepository<Photo>) : GetPhotosForAlbumInteractor {

    override suspend operator fun invoke(albumId: Long): List<Photo> {
        return repository.getAll(PhotosForAlbumQuery(albumId), CacheOperation)
    }
}