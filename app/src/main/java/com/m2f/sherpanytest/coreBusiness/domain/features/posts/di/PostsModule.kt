package com.m2f.sherpanytest.coreBusiness.domain.features.posts.di

import com.m2f.sherpanytest.coreBusiness.domain.features.posts.DefaultGetPostsInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.GetPostsInteractor
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

}