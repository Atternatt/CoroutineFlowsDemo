package com.m2f.sherpanytest.coreBusiness.domain.features.posts.di

import com.m2f.sherpanytest.Database
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.DataSourceMapper
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.VoidDeleteDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.VoidGetDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.VoidPutDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.CacheRepository
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.DeleteRepository
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.GetRepository
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.withMapping
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
    fun providesRemovePostInteractro(interactor: DefaultRemovePostInteractor): RemovePostInteractor = interactor

    @Provides
    @ActivityRetainedScoped
    fun providesPostsRepository(
        getPostsNetworkDatasource: GetPostsNetworkDatasource,
        getPostsDatabaseDataSource: GetPostsDatabaseDataSource,
        putPostsDatabaseDataSource: PutPostDatabaseDataSource,
        deletePostDatabaseDataSource: DeletePostDatabaseDataSource
    ): CacheRepository<PostEntity> {

        val cacheDatasource = DataSourceMapper<PostDBO, PostEntity>(
            getDataSource = getPostsDatabaseDataSource,
            putDataSource = putPostsDatabaseDataSource,
            deleteDataSource = deletePostDatabaseDataSource,
            toOutMapper = PostDBOtoPostEntityMapper(),
            toInMapper = PostentityToPostDBOMapper()
        )

        return CacheRepository(
            getMain = getPostsNetworkDatasource,
            putMain = VoidPutDataSource(),
            deleteMain = VoidDeleteDataSource(),
            getCache = cacheDatasource,
            putCache = cacheDatasource,
            deleteCache = cacheDatasource
        )
    }

    @Provides
    @ActivityRetainedScoped
    fun providesGetRepository(cacheRepo: CacheRepository<PostEntity>): GetRepository<Post> = cacheRepo.withMapping(PostEntityToPostMapper())

    @Provides
    @ActivityRetainedScoped
    @Posts
    fun providesDeleteRepository(cacheRepo: CacheRepository<PostEntity>): DeleteRepository = cacheRepo

    @Provides
    @ActivityRetainedScoped
    fun providesPostQueries(database: Database): PostDBOQueries = database.postDBOQueries

}