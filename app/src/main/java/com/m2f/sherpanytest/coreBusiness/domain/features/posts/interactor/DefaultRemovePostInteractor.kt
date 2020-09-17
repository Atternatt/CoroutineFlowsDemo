package com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor

import com.m2f.sherpanytest.coreBusiness.arch.data.operation.CacheOperation
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.DeleteRepository
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.queries.PostQuery
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.di.Posts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultRemovePostInteractor @Inject constructor(
    @Posts private val deleteRepository: DeleteRepository,
    private val coroutineScope: CoroutineScope
) :
    RemovePostInteractor {

    override suspend fun invoke(postId: Int) = withContext(coroutineScope.coroutineContext) {
        deleteRepository.delete(PostQuery(postId.toLong()), CacheOperation)
    }
}