package com.m2f.sherpanytest.features.posts

import androidx.lifecycle.*
import com.m2f.sherpanytest.coreBusiness.arch.data.error.DataNotFoundException
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor.GetPostsInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor.RemovePostInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.users.interactor.GetUserInteractor
import com.m2f.sherpanytest.features.posts.model.PostUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val getPostsInteractor: GetPostsInteractor,
    private val getUserInteractor: GetUserInteractor,
    private val removePostInteractor: RemovePostInteractor
) :
    ViewModel() {

    //if we set a default value in the constructor it gets propagated when subscribed in the bindings
    //but this time we want to have more control over it so we'll skip a constructor initialization.
    private val refreshData = MutableLiveData<Boolean>()

    val filter: LiveData<String> = MutableLiveData("")

    val debouncedFilter = filter
        .asFlow()
        .debounce(350L)
        .distinctUntilChanged()
        .asLiveData(viewModelScope.coroutineContext)

    val posts: LiveData<List<PostUI>> = refreshData.switchMap { forceRefresh ->
        _isLoading.value = true
        getPostsInteractor(forceRefresh = forceRefresh)
            .flatMapConcat {
                flowOf(it.asFlow()
                    .map { post ->
                        val email = try {
                            getUserInteractor(post.userId.toLong()).email
                        } catch (ex: DataNotFoundException) {
                            //fixme: posible race condition detected. FetchDataWorker could not be stored data into database yet
                            "Unknown mail"
                        }
                        PostUI(post.id, post.title, email)
                    }
                    .toList())
            }
            .flowOn(Dispatchers.Default)
            .onCompletion { _isLoading.value = false }
            .catch { emit(emptyList<PostUI>()) }
            .asLiveData(viewModelScope.coroutineContext)
    }

    private val _isLoading = MutableLiveData(false)

    fun updatefilter(string: String) {
        (filter as MutableLiveData).value = string
    }

    fun retrievePosts(forceRefresh: Boolean) {
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