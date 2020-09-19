package com.m2f.sherpanytest.di.workers

import androidx.work.WorkerFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class WorkerFacotryBuilder {

  @Binds
  abstract fun providesWorkerFactory(factory: SherpanyWorkerFactory): WorkerFactory
}