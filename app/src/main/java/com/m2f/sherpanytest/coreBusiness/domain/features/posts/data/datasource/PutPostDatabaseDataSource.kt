package com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.datasource

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.FlowPutDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.error.DataNotFoundException
import com.m2f.sherpanytest.coreBusiness.arch.data.error.QueryNotSupportedException
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.queries.PostQuery
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.queries.PostsQuery
import com.m2f.sherpanytest.sqldelight.data.PostDBO
import com.m2f.sherpanytest.sqldelight.data.PostDBOQueries
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class PutPostDatabaseDataSource @Inject constructor(private val queries: PostDBOQueries) :
    FlowPutDataSource<PostDBO> {
    override fun put(query: Query, value: PostDBO?): Flow<PostDBO> = flow {
        when {
            query is PostQuery && value != null -> {
                queries.insertOrReplace(value)
                emit(value)
            }
            else -> throw QueryNotSupportedException()
        }
    }

    override fun putAll(query: Query, value: List<PostDBO>?): Flow<List<PostDBO>> {
        if (query == PostsQuery) {
            if (!value.isNullOrEmpty()) {
                return value.asFlow()
                    .flatMapConcat { put(PostQuery(it.postId), it) }
                    .scan(listOf()) { list, item -> list + item } //we could return value but scanning the result we ensure that we get only the added items.


            } else {
                throw DataNotFoundException("trying to store an empty list")
            }
        } else {
            throw QueryNotSupportedException()
        }

    }

}