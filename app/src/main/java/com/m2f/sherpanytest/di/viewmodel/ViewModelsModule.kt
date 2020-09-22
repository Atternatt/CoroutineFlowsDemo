package com.m2f.sherpanytest.di.viewmodel

import androidx.lifecycle.ViewModel
import com.m2f.sherpanytest.features.posts.PostDetailViewModel
import com.m2f.sherpanytest.features.posts.PostsViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.multibindings.IntoMap


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel::class)
    @ActivityRetainedScoped
    abstract fun postsViewModel(postsViewModel: PostsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostDetailViewModel::class)
    abstract fun postDetailViewModel(postDetailViewModel: PostDetailViewModel): ViewModel
}