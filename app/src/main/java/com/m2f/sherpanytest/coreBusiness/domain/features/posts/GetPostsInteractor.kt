package com.m2f.sherpanytest.coreBusiness.domain.features.posts

import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post


interface GetPostsInteractor {

    suspend operator fun invoke(): List<Post>
}