package com.m2f.sherpanytest.features.posts

import androidx.lifecycle.*
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Album
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post
import com.m2f.sherpanytest.coreBusiness.domain.features.albums.interactor.GetAlbumsForUserInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor.GetPostInteractor
import com.m2f.sherpanytest.features.posts.model.AlbumUI
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class PostDetailViewModel @Inject constructor(
    private val getPostInteractor: GetPostInteractor,
    private val getAlbumsForUserInteractor: GetAlbumsForUserInteractor
) :
    ViewModel() {

    private val _postId: MutableLiveData<Long> = MutableLiveData()

    val postDetail: LiveData<Post> = _postId.switchMap { postId ->
        getPostInteractor(postId).asLiveData(viewModelScope.coroutineContext)
    }

    val albums: LiveData<List<AlbumUI>> = postDetail.switchMap { post ->
        getAlbumsForUserInteractor(post.userId.toLong())
            .map { it.map { AlbumUI(it.id, it.title, listOf("https://via.placeholder.com/600/92c952","https://via.placeholder.com/600/771796","https://via.placeholder.com/600/24f355","https://via.placeholder.com/600/f66b97","https://via.placeholder.com/600/56a8c2","https://via.placeholder.com/600/b0f7cc")) } }
            .asLiveData()
    }

    fun loadPostDetail(postId: Long) {
        _postId.value = postId
    }

}