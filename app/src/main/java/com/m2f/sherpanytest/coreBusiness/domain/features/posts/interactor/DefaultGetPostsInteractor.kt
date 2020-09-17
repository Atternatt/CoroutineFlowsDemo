package com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor

import com.m2f.sherpanytest.coreBusiness.arch.data.operation.CacheSyncOperation
import com.m2f.sherpanytest.coreBusiness.arch.data.operation.MainSyncOperation
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.GetRepository
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.queries.PostsQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DefaultGetPostsInteractor @Inject constructor(
    private val getPostsRepository: GetRepository<Post>,
    private val coroutineScope: CoroutineScope
) :
    GetPostsInteractor {

    override suspend fun invoke(forceRefresh: Boolean): List<Post> = withContext(coroutineScope.coroutineContext) {
        val operation = if(forceRefresh) MainSyncOperation else CacheSyncOperation
        getPostsRepository.getAll(PostsQuery, operation)
    }
}