package com.m2f.sherpanytest.features.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor.GetPostsInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor.RemovePostInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject


class PostsViewModel @Inject constructor(
    private val getPostsInteractor: GetPostsInteractor,
    private val removePostInteractor: RemovePostInteractor
) :
    ViewModel() {

    private val _posts: MutableLiveData<List<Post>> = MutableLiveData<List<Post>>(emptyList())

    val posts: LiveData<List<Post>> = _posts

    private val _isLoading = MutableLiveData(false)

    val isLoading: LiveData<Boolean> = _isLoading

    fun retrievePosts(forceRefresh: Boolean) {
        _isLoading.value = true
        viewModelScope.launch {
            _posts.value = try {
                val posts = getPostsInteractor(forceRefresh = forceRefresh)
                posts
            } catch (e: Exception) {
                emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deletePostWithId(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                removePostInteractor(id)
                _posts.value = getPostsInteractor(forceRefresh = false)
            } catch (ex: Exception) {

            } finally {
                _isLoading.value = false
            }
        }
    }


}