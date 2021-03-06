package com.m2f.sherpanytest.coreBusiness.domain.features.users.data.datasource

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.GetDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.error.DataNotFoundException
import com.m2f.sherpanytest.coreBusiness.arch.data.error.QueryNotSupportedException
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import com.m2f.sherpanytest.coreBusiness.common.configuration.NetworkConfiguration
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.UserEntity
import com.m2f.sherpanytest.coreBusiness.domain.features.users.data.queries.UsersQuery
import io.ktor.client.features.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import javax.inject.Inject


class GetUsersNetworkDatasource @Inject constructor(private val networkConfiguration: NetworkConfiguration) :
    GetDataSource<UserEntity> {

    override suspend fun get(query: Query): UserEntity = throw DataNotFoundException()

    override suspend fun getAll(query: Query): List<UserEntity> {
        return when (query) {
            is UsersQuery -> try {
                with(networkConfiguration) {
                    val result: List<UserEntity> =
                        httpClient.get<String>(users).let { json.decodeFromString(it) }
                    result
                }
            } catch (ex: ClientRequestException) {
                throw DataNotFoundException()
            }
            else -> throw QueryNotSupportedException()
        }
    }
}