package com.m2f.sherpanytest.coreBusiness.domain.features.users.di

import com.m2f.sherpanytest.Database
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.FlowDataSourceMapper
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.VoidFlowDeleteDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.VoidFlowPutDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.flow.FlowCacheRepository
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.flow.FlowGetRepository
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.flow.withMapping
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.UserEntity
import com.m2f.sherpanytest.coreBusiness.common.model.domain.User
import com.m2f.sherpanytest.coreBusiness.common.model.mapper.user.UserDBOToUserEntityMapper
import com.m2f.sherpanytest.coreBusiness.common.model.mapper.user.UsereEntityToUserDBOMapper
import com.m2f.sherpanytest.coreBusiness.common.model.mapper.user.UserentityToUserMapper
import com.m2f.sherpanytest.coreBusiness.domain.features.users.data.datasource.GetUserDatabaseDataSource
import com.m2f.sherpanytest.coreBusiness.domain.features.users.data.datasource.GetUsersNetworkDatasource
import com.m2f.sherpanytest.coreBusiness.domain.features.users.data.datasource.PutUserDatabaseDatasource
import com.m2f.sherpanytest.coreBusiness.domain.features.users.interactor.DefaultGetAllUsersInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.users.interactor.DefaultGetUserInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.users.interactor.GetAllUsersInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.users.interactor.GetUserInteractor
import com.m2f.sherpanytest.sqldelight.data.UserDBO
import com.m2f.sherpanytest.sqldelight.data.UserDBOQueries
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class UserModule {

    @Provides
    @Singleton
    fun providesGetUsersInterctor(interactor: DefaultGetAllUsersInteractor): GetAllUsersInteractor =
        interactor

    @Provides
    @Singleton
    fun providesGetUserInterctor(interactor: DefaultGetUserInteractor): GetUserInteractor =
        interactor

    @Provides
    @Singleton
    fun providesUsersRepository(
        getUsersNetworkDatasource: GetUsersNetworkDatasource,
        getUsersDatabaseDataSource: GetUserDatabaseDataSource,
        putUsersDatabaseDataSource: PutUserDatabaseDatasource
    ): FlowCacheRepository<UserEntity> {

        val cacheDatasource = FlowDataSourceMapper<UserDBO, UserEntity>(
            getDataSource = getUsersDatabaseDataSource,
            putDataSource = putUsersDatabaseDataSource,
            deleteDataSource = VoidFlowDeleteDataSource(),
            toOutMapper = UserDBOToUserEntityMapper(),
            toInMapper = UsereEntityToUserDBOMapper()
        )

        return FlowCacheRepository(
            getMain = getUsersNetworkDatasource,
            putMain = VoidFlowPutDataSource(),
            deleteMain = VoidFlowDeleteDataSource(),
            getCache = cacheDatasource,
            putCache = cacheDatasource,
            deleteCache = cacheDatasource
        )
    }

    @Provides
    @Singleton
    fun providesGetRepository(cacheRepo: FlowCacheRepository<UserEntity>): FlowGetRepository<User> =
        cacheRepo.withMapping(UserentityToUserMapper())

    @Provides
    @Singleton
    fun providesPostQueries(database: Database): UserDBOQueries = database.userDBOQueries
}