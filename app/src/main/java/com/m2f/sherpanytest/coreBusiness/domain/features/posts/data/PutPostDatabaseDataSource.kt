package com.m2f.sherpanytest.coreBusiness.domain.features.posts.data

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.PutDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.queries.PostQuery
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.queries.PostsQuery
import com.m2f.sherpanytest.sqldelight.data.PostDBO
import com.m2f.sherpanytest.sqldelight.data.PostDBOQueries
import javax.inject.Inject


class PutPostDatabaseDataSource @Inject constructor(private val queries: PostDBOQueries) :
    PutDataSource<PostDBO> {
    override suspend fun put(query: Query, value: PostDBO?): PostDBO = when {
        query is PostQuery && value != null -> queries.insertOrReplace(value).let { value }
        else -> throw NotImplementedError()
    }

    override suspend fun putAll(query: Query, value: List<PostDBO>?): List<PostDBO> = when {
        query is PostsQuery && !value.isNullOrEmpty() -> value.forEach { put(PostQuery(it.id), it) }.let { value }
        else -> throw NotImplementedError()
    }
}