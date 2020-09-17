package com.m2f.sherpanytest.coreBusiness.domain.features.posts.data

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.GetDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.error.DataNotFoundException
import com.m2f.sherpanytest.coreBusiness.arch.data.error.QueryNotSupportedException
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import com.m2f.sherpanytest.coreBusiness.common.configuration.NetworkConfiguration
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.PostEntity
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.queries.PostsQuery
import io.ktor.client.features.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import javax.inject.Inject


class GetPostsNetworkDatasource @Inject constructor(private val networkConfiguration: NetworkConfiguration) :
    GetDataSource<PostEntity> {

    override suspend fun get(query: Query): PostEntity = throw NotImplementedError()

    override suspend fun getAll(query: Query): List<PostEntity> = when (query) {
        is PostsQuery -> try {
            with(networkConfiguration) {
                httpClient.get<String>(actors).let { json.decodeFromString(it) }
            }
        } catch (ex: ClientRequestException) {
            throw DataNotFoundException()
        }
        else -> throw QueryNotSupportedException()
    }
}