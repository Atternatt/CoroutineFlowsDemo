package com.m2f.sherpanytest.features.posts

import androidx.lifecycle.*
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor.GetPostsInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class PostsViewModel @Inject constructor(private val getPostsInteractor: GetPostsInteractor) :
    ViewModel(){

    private val _posts: MutableLiveData<List<Post>> = MutableLiveData<List<Post>>(emptyList())

    val posts: LiveData<List<Post>> = _posts

    fun initialize() {
        viewModelScope.launch {
            _posts.value = try {
                val posts = getPostsInteractor()
                posts
            } catch (e: Exception) {
                emptyList()
            }
        }
    }


}