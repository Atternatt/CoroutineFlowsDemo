package com.m2f.sherpanytest.coreBusiness.domain.features.users.interactor

import com.m2f.sherpanytest.coreBusiness.common.model.domain.User
import kotlinx.coroutines.flow.Flow


interface GetUserInteractor {

    suspend operator fun invoke(userId: Long): User
}