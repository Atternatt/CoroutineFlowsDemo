package com.m2f.sherpanytest.coreBusiness.domain.features.photos.data.datasource

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.GetDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.error.DataNotFoundException
import com.m2f.sherpanytest.coreBusiness.arch.data.error.QueryNotSupportedException
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import com.m2f.sherpanytest.coreBusiness.common.configuration.NetworkConfiguration
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.PhotoEntity
import com.m2f.sherpanytest.coreBusiness.domain.features.photos.data.queries.AllPhotosQuery
import io.ktor.client.features.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import javax.inject.Inject


class GetPhotosNetworkDatasource @Inject constructor(private val networkConfiguration: NetworkConfiguration) :
    GetDataSource<PhotoEntity> {

    override suspend fun get(query: Query): PhotoEntity =
        throw DataNotFoundException()

    override suspend fun getAll(query: Query): List<PhotoEntity> {
        return when (query) {
            is AllPhotosQuery -> try {
                with(networkConfiguration) {
                    val result: List<PhotoEntity> =
                        httpClient.get<String>(photos).let { json.decodeFromString(it) }
                    result
                }
            } catch (ex: ClientRequestException) {
                throw DataNotFoundException()
            }
            else -> throw QueryNotSupportedException()
        }
    }
}