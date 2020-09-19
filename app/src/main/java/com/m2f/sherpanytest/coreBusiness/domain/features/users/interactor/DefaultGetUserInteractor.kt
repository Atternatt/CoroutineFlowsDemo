package com.m2f.sherpanytest.coreBusiness.domain.features.users.interactor

import com.m2f.sherpanytest.coreBusiness.arch.data.operation.CacheOperation
import com.m2f.sherpanytest.coreBusiness.arch.data.operation.CacheSyncOperation
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.GetRepository
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.flow.FlowGetRepository
import com.m2f.sherpanytest.coreBusiness.common.model.domain.User
import com.m2f.sherpanytest.coreBusiness.domain.features.users.data.queries.UserQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultGetUserInteractor @Inject constructor(private val getUserRepository: GetRepository<User>) : GetUserInteractor {
    override suspend operator fun invoke(userId: Long): User {
        return getUserRepository.get(UserQuery(userId), CacheOperation)
    }
}