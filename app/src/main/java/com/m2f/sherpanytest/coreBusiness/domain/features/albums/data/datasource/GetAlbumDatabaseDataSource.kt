package com.m2f.sherpanytest.coreBusiness.domain.features.albums.data.datasource

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.FlowGetDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.error.QueryNotSupportedException
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import com.m2f.sherpanytest.coreBusiness.domain.features.albums.data.queries.AlbumQuery
import com.m2f.sherpanytest.coreBusiness.domain.features.albums.data.queries.AlbumsQuery
import com.m2f.sherpanytest.sqldelight.data.AlbumDBO
import com.m2f.sherpanytest.sqldelight.data.AlbumDBOQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAlbumDatabaseDataSource @Inject constructor(private val queries: AlbumDBOQueries) :
    FlowGetDataSource<AlbumDBO> {

    override fun get(query: Query): Flow<AlbumDBO> = when (query) {
        is AlbumQuery -> {
            queries.selectBuId(query.identifier).asFlow().mapToOne()
        }
        else -> throw QueryNotSupportedException()
    }

    override fun getAll(query: Query): Flow<List<AlbumDBO>> = when (query) {
        is AlbumsQuery -> {
            queries.selectAllForUser(query.identifier).asFlow().mapToList()
        }
        else -> throw QueryNotSupportedException()
    }
}