package com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.datasource

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.FlowGetDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.error.DataNotFoundException
import com.m2f.sherpanytest.coreBusiness.arch.data.error.QueryNotSupportedException
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import com.m2f.sherpanytest.coreBusiness.common.configuration.NetworkConfiguration
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.PostEntity
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.queries.PostsQuery
import io.ktor.client.features.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import javax.inject.Inject


class GetPostsNetworkDatasource @Inject constructor(private val networkConfiguration: NetworkConfiguration) :
    FlowGetDataSource<PostEntity> {

    override fun get(query: Query): Flow<PostEntity> = throw DataNotFoundException()

    override fun getAll(query: Query): Flow<List<PostEntity>> = flow {

            when (query) {
                is PostsQuery -> try {
                    with(networkConfiguration) {
                        val result: List<PostEntity> = httpClient.get<String>(posts).let { json.decodeFromString(it) }
                        emit(result)
                    }
                } catch (ex: ClientRequestException) {
                    throw DataNotFoundException()
                }
                else -> throw QueryNotSupportedException()
            }
    }
}