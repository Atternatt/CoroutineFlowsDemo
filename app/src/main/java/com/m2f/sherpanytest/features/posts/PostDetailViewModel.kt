package com.m2f.sherpanytest.features.posts

import androidx.lifecycle.*
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post
import com.m2f.sherpanytest.coreBusiness.domain.features.albums.interactor.GetAlbumsForUserInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.photos.interactor.GetPhotosForAlbumInteractor
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.interactor.GetPostInteractor
import com.m2f.sherpanytest.features.posts.model.AlbumUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class PostDetailViewModel @Inject constructor(
    private val getPostInteractor: GetPostInteractor,
    private val getAlbumsForUserInteractor: GetAlbumsForUserInteractor,
    private val getPhotosForAlbumInteractor: GetPhotosForAlbumInteractor
) :
    ViewModel() {

    private val _postId: MutableLiveData<Long> = MutableLiveData()

    val postDetail: LiveData<Post> = _postId.switchMap { postId ->
        getPostInteractor(postId).asLiveData(viewModelScope.coroutineContext)
    }

    val albums: LiveData<List<AlbumUI>> = postDetail.switchMap { post ->
        getAlbumsForUserInteractor(post.userId.toLong())
            //we can call a map because each flow operator is suspending
            .map {
                it.map { album ->
                    AlbumUI(
                        album.id,
                        album.title,
                        getPhotosForAlbumInteractor(album.id.toLong()).map { it.url })
                }
            }
            .flowOn(Dispatchers.Default)
            .asLiveData()
    }

    fun loadPostDetail(postId: Long) {
        _postId.value = postId
    }

}