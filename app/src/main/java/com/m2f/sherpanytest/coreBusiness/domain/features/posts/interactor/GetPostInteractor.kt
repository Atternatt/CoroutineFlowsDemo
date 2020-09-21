package com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor

import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post
import kotlinx.coroutines.flow.Flow


interface GetPostInteractor {

    operator fun invoke(postId: Long): Flow<Post>
}