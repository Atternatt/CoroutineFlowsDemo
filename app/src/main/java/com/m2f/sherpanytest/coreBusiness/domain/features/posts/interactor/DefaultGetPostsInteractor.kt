package com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor

import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DefaultGetPostsInteractor @Inject constructor(private val coroutineScope: CoroutineScope) :
    GetPostsInteractor {

    override suspend fun invoke(): List<Post> = withContext(coroutineScope.coroutineContext) {
        listOf(
            Post(
                id = 0,
                title = "asperiores ea ipsam voluptatibus modi minima quia sint",
                body = "repellat aliquid praesentium dolorem quo\nsed totam minus non itaque\nnihil labore molestiae sunt dolor eveniet hic recusandae veniam\ntempora et tenetur expedita sunt",
                userId = 300
            )
        )
    }
}