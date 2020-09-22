package com.m2f.sherpanytest.features.background_fetch

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.m2f.sherpanytest.coreBusiness.domain.features.albums.interactor.FetchAlbumsInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.photos.interactor.FetchAllPhotosInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor.GetPostsInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.users.interactor.GetAllUsersInteractor
import com.m2f.sherpanytest.di.workers.AssistedWorkerFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import java.net.UnknownHostException
import javax.inject.Inject


class FetchDataWorker(
    context: Context, workerParams: WorkerParameters,
    private val getPostsInteractor: GetPostsInteractor,
    private val getAllUsersInteractor: GetAllUsersInteractor,
    private val getAlbumsInteractor: FetchAlbumsInteractor,
    private val getPhotosInteractor: FetchAllPhotosInteractor
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val TAG = "FetchDataWorker"
    }

    override suspend fun doWork(): Result = coroutineScope {
        //we are running concurrently all network requests
        try {
            val posts = async {
                getPostsInteractor(forceRefresh = true)
                    .toList()
                    .last()
            }

            val users = async { getAllUsersInteractor(forceRefresh = true) }

            val photos = async { getPhotosInteractor() }

            val albums = async {
                getAlbumsInteractor()
                    .toList()
                    .last()
            }

            val result =
                if (!users.await().isNullOrEmpty() &&
                    !posts.await().isNullOrEmpty() &&
                    !albums.await().isNullOrEmpty() &&
                    !photos.await().isNullOrEmpty()
                ) Result.success() else Result.retry()
            result
        } catch (ex: Exception) {
            if (ex is UnknownHostException) {
                //an UnknownHostException could be catched if we havent got network so we try to refetch the data ASAP (following the retry policy of the worker when created)
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }

    class Factory @Inject constructor(
        private val getPostsInteractor: GetPostsInteractor,
        private val getAllUsersInteractor: GetAllUsersInteractor,
        private val getAlbumsInteractor: FetchAlbumsInteractor,
        private val getPhotosInteractor: FetchAllPhotosInteractor
    ) : AssistedWorkerFactory {
        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker =
            FetchDataWorker(
                appContext,
                params,
                getPostsInteractor,
                getAllUsersInteractor,
                getAlbumsInteractor,
                getPhotosInteractor
            )
    }
}

