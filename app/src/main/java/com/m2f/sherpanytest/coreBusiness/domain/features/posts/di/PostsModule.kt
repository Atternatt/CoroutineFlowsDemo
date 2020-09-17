package com.m2f.sherpanytest.coreBusiness.domain.features.posts.di

import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.VoidDeleteDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.VoidGetDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.VoidPutDataSource
import com.m2f.sherpanytest.coreBusiness.arch.data.datasource.toGetRepository
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.*
import com.m2f.sherpanytest.coreBusiness.common.configuration.NetworkConfiguration
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.PostEntity
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post
import com.m2f.sherpanytest.coreBusiness.common.model.mapper.PostEntityToPostMapper
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.GetPostsNetworkDatasource
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor.DefaultGetPostsInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor.GetPostsInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Singleton


@Module
@InstallIn(ActivityRetainedComponent::class)
class PostsModule {

    @Provides
    @ActivityRetainedScoped
    fun providesGetPostsInterctor(interactor: DefaultGetPostsInteractor): GetPostsInteractor =
        interactor


    @Provides
    @ActivityRetainedScoped
    fun providesPostsRepository(getPostsNetworkDatasource: GetPostsNetworkDatasource): GetRepository<Post> {

        //TODO @Marc -> implement cache
         return CacheRepository(
            getMain = getPostsNetworkDatasource,
            putMain = VoidPutDataSource(),
            deleteMain = VoidDeleteDataSource(),
            getCache = VoidGetDataSource(),
            putCache = VoidPutDataSource(),
            deleteCache = VoidDeleteDataSource()
        ).withMapping(PostEntityToPostMapper())
    }

}