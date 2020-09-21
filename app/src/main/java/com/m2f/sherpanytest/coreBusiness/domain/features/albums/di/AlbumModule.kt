package com.m2f.sherpanytest.coreBusiness.domain.features.albums.di

import com.m2f.sherpanytest.Database
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.FlowDataSourceMapper
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.VoidFlowDeleteDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.VoidFlowPutDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.flow.FlowCacheRepository
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.flow.FlowGetRepository
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.flow.withMapping
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.AlbumEntity
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Album
import com.m2f.sherpanytest.coreBusiness.common.model.mapper.album.AlbumDBOToAlbumEntityMapper
import com.m2f.sherpanytest.coreBusiness.common.model.mapper.album.AlbumEntityToAlbumDBOMapper
import com.m2f.sherpanytest.coreBusiness.common.model.mapper.album.AlbumEntityToAlbumMapper
import com.m2f.sherpanytest.coreBusiness.domain.features.albums.data.datasource.GetAlbumDatabaseDataSource
import com.m2f.sherpanytest.coreBusiness.domain.features.albums.data.datasource.GetAlbumNetworDataSource
import com.m2f.sherpanytest.coreBusiness.domain.features.albums.data.datasource.PutAlbumDatabaseDataSource
import com.m2f.sherpanytest.coreBusiness.domain.features.albums.interactor.DefaultFetchAlbumsInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.albums.interactor.FetchAlbumsInteractor
import com.m2f.sherpanytest.sqldelight.data.AlbumDBO
import com.m2f.sherpanytest.sqldelight.data.AlbumDBOQueries
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class AlbumModule {

    @Provides
    @Singleton
    fun providesFetchAlbumsInteractor(interactor: DefaultFetchAlbumsInteractor): FetchAlbumsInteractor = interactor

    @Provides
    @Singleton
    fun providesAlbumRepository(
        getAlbumNetworkDatasource: GetAlbumNetworDataSource,
        getAlbumDatabaseDataSource: GetAlbumDatabaseDataSource,
        putAlbumDatabaseDataSource: PutAlbumDatabaseDataSource
    ): FlowCacheRepository<AlbumEntity> {

        val cacheDatasource = FlowDataSourceMapper<AlbumDBO, AlbumEntity>(
            getDataSource = getAlbumDatabaseDataSource,
            putDataSource = putAlbumDatabaseDataSource,
            deleteDataSource = VoidFlowDeleteDataSource(),
            toOutMapper = AlbumDBOToAlbumEntityMapper(),
            toInMapper = AlbumEntityToAlbumDBOMapper()
        )

        return FlowCacheRepository(
            getMain = getAlbumNetworkDatasource,
            putMain = VoidFlowPutDataSource(),
            deleteMain = VoidFlowDeleteDataSource(),
            getCache = cacheDatasource,
            putCache = cacheDatasource,
            deleteCache = cacheDatasource
        )
    }

    @Provides
    @Singleton
    fun providesGetRepository(cacheRepo: FlowCacheRepository<AlbumEntity>): FlowGetRepository<Album> =
        cacheRepo.withMapping(AlbumEntityToAlbumMapper())

    @Provides
    @Singleton
    fun providesPostQueries(database: Database): AlbumDBOQueries = database.albumDBOQueries
}