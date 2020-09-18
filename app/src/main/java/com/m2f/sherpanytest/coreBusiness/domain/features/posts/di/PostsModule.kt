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
import com.m2f.sherpanytest.coreBusiness.common.model.mapper.PostDBOtoPostEntityMapper
import com.m2f.sherpanytest.coreBusiness.common.model.mapper.PostEntityToPostMapper
import com.m2f.sherpanytest.coreBusiness.common.model.mapper.PostentityToPostDBOMapper
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.DeletePostDatabaseDataSource
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.GetPostsDatabaseDataSource
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.GetPostsNetworkDatasource
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.PutPostDatabaseDataSource
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor.DefaultGetPostsInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor.DefaultRemovePostInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor.GetPostsInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor.RemovePostInteractor
import com.m2f.sherpanytest.sqldelight.data.PostDBO
import com.m2f.sherpanytest.sqldelight.data.PostDBOQueries
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@Module
@InstallIn(ActivityRetainedComponent::class)
class PostsModule {

    @Provides
    @ActivityRetainedScoped
    fun providesGetPostsInterctor(interactor: DefaultGetPostsInteractor): GetPostsInteractor =
        interactor

    @Provides
    @ActivityRetainedScoped
    fun providesRemovePostInteractro(interactor: DefaultRemovePostInteractor): RemovePostInteractor =
        interactor

    @Provides
    @ActivityRetainedScoped
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
    @ActivityRetainedScoped
    fun providesGetRepository(cacheRepo: FlowCacheRepository<PostEntity>): FlowGetRepository<Post> =
        cacheRepo.withMapping(PostEntityToPostMapper())

    @Provides
    @ActivityRetainedScoped
    @Posts
    fun providesDeleteRepository(cacheRepo: FlowCacheRepository<PostEntity>): FlowDeleteRepository =
        cacheRepo

    @Provides
    @ActivityRetainedScoped
    fun providesPostQueries(database: Database): PostDBOQueries = database.postDBOQueries

}