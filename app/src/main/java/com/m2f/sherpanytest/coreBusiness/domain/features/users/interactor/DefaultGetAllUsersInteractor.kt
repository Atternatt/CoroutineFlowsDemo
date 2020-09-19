package com.m2f.sherpanytest.coreBusiness.domain.features.users.interactor

import com.m2f.sherpanytest.coreBusiness.arch.data.operation.CacheOperation
import com.m2f.sherpanytest.coreBusiness.arch.data.operation.MainSyncOperation
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.GetRepository
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.flow.FlowGetRepository
import com.m2f.sherpanytest.coreBusiness.common.model.domain.User
import com.m2f.sherpanytest.coreBusiness.domain.features.users.data.queries.UsersQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultGetAllUsersInteractor @Inject constructor(private val getUserRepository: GetRepository<User>) :
    GetAllUsersInteractor {
    override suspend operator fun invoke(forceRefresh: Boolean): List<User> {
        val operation = if (forceRefresh) MainSyncOperation else CacheOperation
        return getUserRepository.getAll(UsersQuery, operation)
    }
}