package com.m2f.sherpanytest.features.posts

import androidx.lifecycle.*
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor.GetPostsInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor.RemovePostInteractor
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    //private val getPostsInteractor: GetPostsInteractor,
    private val removePostInteractor: RemovePostInteractor
) :
    ViewModel() {

    private val refreshData = MutableLiveData(true)

    val filter: LiveData<String> = MutableLiveData("")

    val debouncedFilter = filter
        .asFlow()
        .debounce(350L)
        .distinctUntilChanged()
        .asLiveData(viewModelScope.coroutineContext)

    val posts: LiveData<List<Post>> = refreshData.switchMap { forceRefresh ->
        flowOf(emptyList<Post>())
            .onCompletion { _isLoading.value = false }
            .catch { emptyList<Post>().asFlow() }
            .asLiveData(viewModelScope.coroutineContext)
    }

    private val _isLoading = MutableLiveData(false)

    val isLoading: LiveData<Boolean> = _isLoading

    fun updatefilter(string: String) {
        (filter as MutableLiveData).value = string
    }

    fun retrievePosts(forceRefresh: Boolean) {
        _isLoading.value = true
        refreshData.value = forceRefresh
    }

    fun deletePostWithId(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            removePostInteractor(id)
                .onCompletion { _isLoading.value = false }
                .collect { retrievePosts(forceRefresh = false) }
        }
    }

}