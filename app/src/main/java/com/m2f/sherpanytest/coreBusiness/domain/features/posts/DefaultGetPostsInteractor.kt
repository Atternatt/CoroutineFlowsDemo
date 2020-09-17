package com.m2f.sherpanytest.coreBusiness.domain.features.posts

import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DefaultGetPostsInteractor @Inject constructor(private val coroutineScope: CoroutineScope) :
    GetPostsInteractor {

    override suspend fun invoke(): List<Post> = withContext(coroutineScope.coroutineContext) {
        emptyList()
    }
}