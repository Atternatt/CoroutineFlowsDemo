package com.m2f.sherpanytest.coreBusiness.domain.features.photos.data.datasource

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.PutDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.error.DataNotFoundException
import com.m2f.sherpanytest.coreBusiness.arch.data.error.QueryNotSupportedException
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import com.m2f.sherpanytest.coreBusiness.domain.features.photos.data.queries.AllPhotosQuery
import com.m2f.sherpanytest.coreBusiness.domain.features.photos.data.queries.PhotoQuery
import com.m2f.sherpanytest.sqldelight.data.PhotoDBO
import com.m2f.sherpanytest.sqldelight.data.PhotoDBOQueries
import javax.inject.Inject


class PutPhotoDatabaseDataSource @Inject constructor(private val queries: PhotoDBOQueries) :
    PutDataSource<PhotoDBO> {

    override suspend fun put(query: Query, value: PhotoDBO?): PhotoDBO {
        return when {
            query is PhotoQuery && value != null -> {
                queries.insertOrReplace(value)
                value
            }
            else -> throw QueryNotSupportedException()
        }
    }

    override suspend fun putAll(query: Query, value: List<PhotoDBO>?): List<PhotoDBO> {
        return if (query is AllPhotosQuery) {
            if (!value.isNullOrEmpty()) {
                value.map { put(PhotoQuery(it.albumId), it) }
            } else {
                throw DataNotFoundException("trying to store an empty list")
            }
        } else {
            throw QueryNotSupportedException()
        }
    }


}