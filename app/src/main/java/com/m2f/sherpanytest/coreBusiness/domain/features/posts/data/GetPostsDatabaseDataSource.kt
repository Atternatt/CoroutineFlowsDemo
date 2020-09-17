package com.m2f.sherpanytest.coreBusiness.domain.features.posts.data

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.GetDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.queries.PostsQuery
import com.m2f.sherpanytest.sqldelight.data.PostDBO
import com.m2f.sherpanytest.sqldelight.data.PostDBOQueries
import javax.inject.Inject


class GetPostsDatabaseDataSource @Inject constructor(
    private val queries: PostDBOQueries
): GetDataSource<PostDBO> {
    override suspend fun get(query: Query): PostDBO = throw NotImplementedError()

    override suspend fun getAll(query: Query): List<PostDBO> = when(query) {
        is PostsQuery -> { queries.selectAll().executeAsList()}
        else -> throw NotImplementedError()
    }
}