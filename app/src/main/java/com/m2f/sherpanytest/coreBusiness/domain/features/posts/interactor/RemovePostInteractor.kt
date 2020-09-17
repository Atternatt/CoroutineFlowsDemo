package com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor


interface RemovePostInteractor {

    suspend operator fun invoke(postId: Int)
}