package com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor

import com.m2f.sherpanytest.coreBusiness.arch.data.operation.CacheOperation
import com.m2f.sherpanytest.coreBusiness.arch.data.repository.flow.FlowGetRepository
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.queries.PostQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultGetPostInteractor @Inject constructor(private val getRepository: FlowGetRepository<Post>) :
    GetPostInteractor {

    override operator fun invoke(postId: Long): Flow<Post> {
        //we use a cache operation because there isn't any Network call to a singl user so
        //we just get it from database if some (otherwise a DataNotFoundExeption will be thrown)
        return getRepository.get(PostQuery(postId), CacheOperation)
    }
}