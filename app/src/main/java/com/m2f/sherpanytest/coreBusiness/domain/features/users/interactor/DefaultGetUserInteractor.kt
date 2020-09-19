package com.m2f.sherpanytest.coreBusiness.domain.features.users.interactor

import com.m2f.sherpanytest.coreBusiness.arch.data.repository.flow.FlowGetRepository
import com.m2f.sherpanytest.coreBusiness.common.model.domain.User
import com.m2f.sherpanytest.coreBusiness.domain.features.users.data.queries.UserQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultGetUserInteractor @Inject constructor(private val getUserRepository: FlowGetRepository<User>) : GetUserInteractor {
    override fun invoke(userId: Long): Flow<User> {
        return getUserRepository.get(UserQuery(userId))
    }
}