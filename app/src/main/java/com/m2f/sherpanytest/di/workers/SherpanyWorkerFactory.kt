package com.m2f.sherpanytest.di.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider


class SherpanyWorkerFactory @Inject constructor(
    private val workerFactories: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<AssistedWorkerFactory>>
) : WorkerFactory() {
  override fun createWorker(
      appContext: Context,
      workerClassName: String,
      workerParameters: WorkerParameters
  ): ListenableWorker? {
    val foundEntry =
        workerFactories.entries.find {
          try {
            Class.forName(workerClassName).isAssignableFrom(it.key)
          } catch (e: Exception) {
            false
          }
        }
    val factoryProvider = foundEntry?.value
    return factoryProvider?.get()?.create(appContext, workerParameters)
  }
}