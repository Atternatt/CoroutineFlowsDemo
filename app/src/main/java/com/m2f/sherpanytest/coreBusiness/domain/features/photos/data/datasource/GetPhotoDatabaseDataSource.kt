package com.m2f.sherpanytest.coreBusiness.domain.features.photos.data.datasource

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.GetDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.error.DataNotFoundException
import com.m2f.sherpanytest.coreBusiness.arch.data.error.QueryNotSupportedException
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import com.m2f.sherpanytest.coreBusiness.domain.features.photos.data.queries.PhotosForAlbumQuery
import com.m2f.sherpanytest.sqldelight.data.PhotoDBO
import com.m2f.sherpanytest.sqldelight.data.PhotoDBOQueries
import javax.inject.Inject


class GetPhotoDatabaseDataSource @Inject constructor(private val queries: PhotoDBOQueries) :
    GetDataSource<PhotoDBO> {

    override suspend fun get(query: Query): PhotoDBO = throw DataNotFoundException()

    override suspend fun getAll(query: Query): List<PhotoDBO> = when (query) {
        is PhotosForAlbumQuery -> {
            queries.selectAllForUser(query.identifier).executeAsList()
        }
        else -> throw QueryNotSupportedException()
    }
}