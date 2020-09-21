package com.m2f.sherpanytest.coreBusiness.domain.features.photos.di

import com.m2f.sherpanytest.Database
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.DataSourceMapper
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.VoidDeleteDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.VoidPutDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.CacheRepository
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.GetRepository
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.withMapping
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.PhotoEntity
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Photo
import com.m2f.sherpanytest.coreBusiness.common.model.mapper.photo.PhotoDBOToPhotoEntityMapper
import com.m2f.sherpanytest.coreBusiness.common.model.mapper.photo.PhotoEntityToPhotoDBOMapper
import com.m2f.sherpanytest.coreBusiness.common.model.mapper.photo.PhotoEntityToPhotoMapper
import com.m2f.sherpanytest.coreBusiness.domain.features.photos.data.datasource.GetPhotoDatabaseDataSource
import com.m2f.sherpanytest.coreBusiness.domain.features.photos.data.datasource.GetPhotosNetworkDatasource
import com.m2f.sherpanytest.coreBusiness.domain.features.photos.data.datasource.PutPhotoDatabaseDataSource
import com.m2f.sherpanytest.coreBusiness.domain.features.photos.interactor.DefaultFetchAllPhotosInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.photos.interactor.FetchAllPhotosInteractor
import com.m2f.sherpanytest.sqldelight.data.PhotoDBO
import com.m2f.sherpanytest.sqldelight.data.PhotoDBOQueries
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class PhotosModule {

    @Provides
    @Singleton
    fun providesFetchPhotosInteractor(interactor: DefaultFetchAllPhotosInteractor): FetchAllPhotosInteractor =
        interactor

    @Provides
    @Singleton
    fun providesPhotosRepository(
        getPhotosNetworkDatasource: GetPhotosNetworkDatasource,
        getPhotoDatabaseDataSource: GetPhotoDatabaseDataSource,
        putPhotoDatabaseDataSource: PutPhotoDatabaseDataSource
    ): CacheRepository<PhotoEntity> {

        val cacheDatasource = DataSourceMapper<PhotoDBO, PhotoEntity>(
            getDataSource = getPhotoDatabaseDataSource,
            putDataSource = putPhotoDatabaseDataSource,
            deleteDataSource = VoidDeleteDataSource(),
            toOutMapper = PhotoDBOToPhotoEntityMapper(),
            toInMapper = PhotoEntityToPhotoDBOMapper()
        )

        return CacheRepository<PhotoEntity>(
            getMain = getPhotosNetworkDatasource,
            putMain = VoidPutDataSource(),
            deleteMain = VoidDeleteDataSource(),
            getCache = cacheDatasource,
            putCache = cacheDatasource,
            deleteCache = cacheDatasource
        )
    }

    @Provides
    @Singleton
    fun providesGetPhotosRepository(cacheRepo: CacheRepository<PhotoEntity>): GetRepository<Photo> =
        cacheRepo.withMapping(PhotoEntityToPhotoMapper())

    @Provides
    @Singleton
    fun providesPhotosQueries(database: Database): PhotoDBOQueries = database.photoDBOQueries
}