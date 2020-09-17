package com.m2f.sherpanytest.coreBusiness.domain.features.posts.data

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.DeleteDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.error.QueryNotSupportedException
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.queries.PostQuery
import com.m2f.sherpanytest.sqldelight.data.PostDBOQueries
import javax.inject.Inject


class DeletePostDatabaseDataSource @Inject constructor(private val queries: PostDBOQueries) :
    DeleteDataSource {

    override suspend fun delete(query: Query) = when(query) {
        is PostQuery -> queries.deletePostWithId(query.id)
        else -> throw QueryNotSupportedException()
    }

    override suspend fun deleteAll(query: Query) = throw NotImplementedError()
}