package com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor

import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post


interface GetPostsInteractor {

    suspend operator fun invoke(forceRefresh: Boolean): List<Post>
}