package com.m2f.sherpanytest.features.posts

import androidx.lifecycle.ViewModel
import com.m2f.sherpanytest.coreBusiness.domain.features.posts.GetPostsInteractor
import javax.inject.Inject


class PostsViewModel @Inject constructor(private val getPostsInteractor: GetPostsInteractor): ViewModel() {
}