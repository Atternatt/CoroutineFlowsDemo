package com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.datasource

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.FlowDeleteDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.error.QueryNotSupportedException
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.queries.PostQuery
import com.m2f.sherpanytest.sqldelight.data.PostDBOQueries
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class DeletePostDatabaseDataSource @Inject constructor(private val queries: PostDBOQueries) :
    FlowDeleteDataSource {

    override fun delete(query: Query) = flow {
        when (query) {
            is PostQuery -> {
                queries.deletePostWithId(query.id)
                emit(Unit)
            }
            else -> throw QueryNotSupportedException()
    }
    }

    override fun deleteAll(query: Query) = throw NotImplementedError()
}