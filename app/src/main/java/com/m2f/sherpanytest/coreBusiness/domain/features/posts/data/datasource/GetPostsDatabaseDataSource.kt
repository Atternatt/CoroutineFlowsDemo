package com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.datasource

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.FlowGetDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.error.QueryNotSupportedException
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.queries.PostQuery
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.queries.PostsQuery
import com.m2f.sherpanytest.coreBusiness.domain.features.users.data.queries.UserQuery
import com.m2f.sherpanytest.sqldelight.data.PostDBO
import com.m2f.sherpanytest.sqldelight.data.PostDBOQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class GetPostsDatabaseDataSource @Inject constructor(
    private val queries: PostDBOQueries
) : FlowGetDataSource<PostDBO> {
    override fun get(query: Query): Flow<PostDBO> = when (query) {
        is PostQuery -> {
            queries.selectPostById(query.identifier).asFlow().mapToOne()
        }
        else -> throw QueryNotSupportedException()
    }

    override fun getAll(query: Query): Flow<List<PostDBO>> = when (query) {
        is PostsQuery -> {
            queries.selectAll().asFlow().mapToList()
        }
        else -> throw NotImplementedError()
    }}