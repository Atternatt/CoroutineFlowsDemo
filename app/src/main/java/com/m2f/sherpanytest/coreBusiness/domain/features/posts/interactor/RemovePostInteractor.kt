package com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor

import kotlinx.coroutines.flow.Flow


interface RemovePostInteractor {

    operator fun invoke(postId: Int): Flow<Unit>
}