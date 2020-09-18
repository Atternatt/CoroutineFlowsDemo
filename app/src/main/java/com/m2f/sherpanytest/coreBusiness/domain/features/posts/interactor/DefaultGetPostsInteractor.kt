package com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor

import com.m2f.sherpanytest.coreBusiness.arch.data.operation.CacheSyncOperation
import com.m2f.sherpanytest.coreBusiness.arch.data.operation.MainSyncOperation
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.GetRepository
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.flow.FlowGetRepository
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.queries.PostsQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DefaultGetPostsInteractor @Inject constructor(
    private val getPostsRepository: FlowGetRepository<Post>
) :
    GetPostsInteractor {

    override operator fun invoke(forceRefresh: Boolean): Flow<List<Post>> {
        val operation = if(forceRefresh) MainSyncOperation else CacheSyncOperation
        return getPostsRepository.getAll(PostsQuery, operation)
    }
}