package com.m2f.sherpanytest.coreBusiness.domain.features.albums.data.datasource

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.FlowPutDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.error.DataNotFoundException
import com.m2f.sherpanytest.coreBusiness.arch.data.error.QueryNotSupportedException
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import com.m2f.sherpanytest.coreBusiness.domain.features.albums.data.queries.AlbumQuery
import com.m2f.sherpanytest.coreBusiness.domain.features.albums.data.queries.AllAlbumsQuery
import com.m2f.sherpanytest.sqldelight.data.AlbumDBO
import com.m2f.sherpanytest.sqldelight.data.AlbumDBOQueries
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class PutAlbumDatabaseDataSource @Inject constructor(private val queries: AlbumDBOQueries) :
    FlowPutDataSource<AlbumDBO> {
    override fun put(query: Query, value: AlbumDBO?): Flow<AlbumDBO> = flow {
        when {
            query is AlbumQuery && value != null -> {
                queries.insertOrReplace(value)
                emit(value)
            }
            else -> throw QueryNotSupportedException()
        }
    }

    override fun putAll(query: Query, value: List<AlbumDBO>?): Flow<List<AlbumDBO>> {
        if (query == AllAlbumsQuery) {
            if (!value.isNullOrEmpty()) {
                return value.asFlow()
                    .flatMapConcat { put(AlbumQuery(it.id), it) }
                    .scan(listOf()) { list, item -> list + item } //we could return value but scanning the result we ensure that we get only the added items.
            } else {
                throw DataNotFoundException("trying to store an empty list")
            }
        } else {
            throw QueryNotSupportedException()
        }

    }

}