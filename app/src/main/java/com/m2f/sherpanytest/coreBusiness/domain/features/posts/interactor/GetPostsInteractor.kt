package com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor

import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post
import kotlinx.coroutines.flow.Flow


interface GetPostsInteractor {

    operator fun invoke(forceRefresh: Boolean): Flow<List<Post>>
}