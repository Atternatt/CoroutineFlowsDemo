package com.m2f.sherpanytest.di.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

/**
 * This interface is used to create Workers manually in order to be able to inject them by constructor.
 * params are provided asyncronously we need to use assited injection.
 */
interface AssistedWorkerFactory {
  fun create(appContext: Context, params: WorkerParameters): ListenableWorker
}