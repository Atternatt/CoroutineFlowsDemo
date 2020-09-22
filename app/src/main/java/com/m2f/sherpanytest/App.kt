package com.m2f.sherpanytest

import android.app.Application
import androidx.work.*
import com.m2f.sherpanytest.features.background_fetch.FetchDataWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltAndroidApp
class App: Application() {

    @Inject
    lateinit var workerFactory: WorkerFactory

    override fun onCreate() {
        super.onCreate()
        initworkManager()
        scheduleDataFetching()
    }

    private fun initworkManager() {
        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(workerFactory).build())
    }

    private fun scheduleDataFetching() {
        val fetchRequest = PeriodicWorkRequest.Builder(FetchDataWorker::class.java, 15L, TimeUnit.MINUTES)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                1L,
                TimeUnit.MINUTES)
            .addTag(FetchDataWorker.TAG)
            .build()
        WorkManager.getInstance().enqueue(fetchRequest)

    }
}