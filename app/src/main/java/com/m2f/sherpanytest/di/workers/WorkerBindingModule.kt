package com.m2f.sherpanytest.di.workers

import com.m2f.sherpanytest.features.background_fetch.FetchDataWorker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ApplicationComponent::class)
interface WorkerBindingModule {


    @Binds
    @IntoMap
    @WorkerKey(FetchDataWorker::class)
    fun bindsFetchWorker(factory: FetchDataWorker.Factory): AssistedWorkerFactory
}