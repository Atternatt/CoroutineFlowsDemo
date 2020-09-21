package com.m2f.sherpanytest.coreBusiness.domain.features.albums.data.datasource

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.FlowGetDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.error.DataNotFoundException
import com.m2f.sherpanytest.coreBusiness.arch.data.error.QueryNotSupportedException
import com.m2f.sherpanytest.coreBusiness.arch.data.query.Query
import com.m2f.sherpanytest.coreBusiness.common.configuration.NetworkConfiguration
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.AlbumEntity
import com.m2f.sherpanytest.coreBusiness.domain.features.albums.data.queries.AllAlbumsQuery
import io.ktor.client.features.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import javax.inject.Inject


class GetAlbumNetworDataSource @Inject constructor(private val networkConfiguration: NetworkConfiguration) :
    FlowGetDataSource<AlbumEntity> {

    override fun get(query: Query): Flow<AlbumEntity> = throw DataNotFoundException()

    override fun getAll(query: Query): Flow<List<AlbumEntity>> = flow {
        when (query) {
            is AllAlbumsQuery -> try {
                with(networkConfiguration) {
                    val result: String =
                        httpClient.get<String>(albums)

                    val parsedResult: List<AlbumEntity> = result.let { json.decodeFromString(it) }
                    emit(parsedResult)
                }
            } catch (ex: ClientRequestException) {
                throw DataNotFoundException()
            }
            else -> throw QueryNotSupportedException()
        }
    }
}