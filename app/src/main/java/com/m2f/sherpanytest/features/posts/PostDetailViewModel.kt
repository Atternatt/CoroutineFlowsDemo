package com.m2f.sherpanytest.features.posts

import androidx.lifecycle.*
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor.GetPostInteractor
import javax.inject.Inject


class PostDetailViewModel @Inject constructor(private val getPostInteractor: GetPostInteractor) :
    ViewModel() {

    private val _postId: MutableLiveData<Long> = MutableLiveData()

    val postDetail: LiveData<Post> = _postId.switchMap { postId ->
        getPostInteractor(postId).asLiveData(viewModelScope.coroutineContext)
    }

    fun loadPostDetail(postId: Long) {
        _postId.value = postId
    }

}