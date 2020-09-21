package com.m2f.sherpanytest.coreBusiness.domain.features.posts.di

import com.m2f.sherpanytest.Database
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.FlowDataSourceMapper
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.VoidFlowDeleteDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.flow.VoidFlowPutDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.flow.FlowCacheRepository
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.flow.FlowDeleteRepository
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.flow.FlowGetRepository
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.flow.withMapping
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.PostEntity
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post
import com.m2f.sherpanytest.coreBusiness.common.model.mapper.posts.PostDBOtoPostEntityMapper
import com.m2f.sherpanytest.coreBusiness.common.model.mapper.posts.PostEntityToPostMapper
import com.m2f.sherpanytest.coreBusiness.common.model.mapper.posts.PostentityToPostDBOMapper
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.datasource.DeletePostDatabaseDataSource
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.datasource.GetPostsDatabaseDataSource
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.datasource.GetPostsNetworkDatasource
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.datasource.PutPostDatabaseDataSource
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor.*
import com.m2f.sherpanytest.sqldelight.data.PostDBO
import com.m2f.sherpanytest.sqldelight.data.PostDBOQueries
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class PostsModule {

    @Provides
    @Singleton
    fun providesGetPostsInterctor(interactor: DefaultGetPostsInteractor): GetPostsInteractor =
        interactor

    @Provides
    @Singleton
    fun providesRemovePostInteractro(interactor: DefaultRemovePostInteractor): RemovePostInteractor =
        interactor

    @Provides
    @Singleton
    fun providesGetPostDetailInteractro(interactor: DefaultGetPostInteractor): GetPostInteractor =
        interactor

    @Provides
    @Singleton
    fun providesPostsRepository(
        getPostsNetworkDatasource: GetPostsNetworkDatasource,
        getPostsDatabaseDataSource: GetPostsDatabaseDataSource,
        putPostsDatabaseDataSource: PutPostDatabaseDataSource,
        deletePostDatabaseDataSource: DeletePostDatabaseDataSource
    ): FlowCacheRepository<PostEntity> {

        val cacheDatasource = FlowDataSourceMapper<PostDBO, PostEntity>(
            getDataSource = getPostsDatabaseDataSource,
            putDataSource = putPostsDatabaseDataSource,
            deleteDataSource = deletePostDatabaseDataSource,
            toOutMapper = PostDBOtoPostEntityMapper(),
            toInMapper = PostentityToPostDBOMapper()
        )

        return FlowCacheRepository(
            getMain = getPostsNetworkDatasource,
            putMain = VoidFlowPutDataSource(),
            deleteMain = VoidFlowDeleteDataSource(),
            getCache = cacheDatasource,
            putCache = cacheDatasource,
            deleteCache = cacheDatasource
        )
    }

    @Provides
    @Singleton
    fun providesGetRepository(cacheRepo: FlowCacheRepository<PostEntity>): FlowGetRepository<Post> =
        cacheRepo.withMapping(PostEntityToPostMapper())

    @Provides
    @Posts
    @Singleton
    fun providesDeleteRepository(cacheRepo: FlowCacheRepository<PostEntity>): FlowDeleteRepository =
        cacheRepo

    @Provides
    @Singleton
    fun providesPostQueries(database: Database): PostDBOQueries = database.postDBOQueries

}