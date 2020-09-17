package com.m2f.sherpanytest.di.viewmodel

import androidx.lifecycle.ViewModel
import com.m2f.sherpanytest.features.posts.PostsViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.multibindings.IntoMap


@Module
@InstallIn(FragmentComponent::class)
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel::class)
    abstract fun postsViewModel(postsViewModel: PostsViewModel): ViewModel
}