package com.m2f.sherpanytest.features.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.m2f.sherpanytest.databinding.FragmentItemDetailBinding
import com.m2f.sherpanytest.di.viewmodel.ViewModelFactory
import com.m2f.sherpanytest.features.posts.model.AlbumUI
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ItemDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val vm: PostDetailViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(PostDetailViewModel::class.java)
    }

    private val postId: Long by lazy { (arguments?.getLong(ARG_ITEM_ID, -1))!! }

    private val adapter: AlbumsAdapter by lazy { AlbumsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentItemDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@ItemDetailFragment
            viewModel = vm
            listAlbums.adapter = adapter
            listAlbums.layoutManager = object : LinearLayoutManager(requireContext()) {
                override fun canScrollVertically(): Boolean = false
            }

        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (postId >= 0) {
            vm.loadPostDetail(postId)
        }
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"


        @BindingAdapter("bind:albums")
        @JvmStatic
        fun RecyclerView.bindAlbums(list: List<AlbumUI>?) {
            if (list != null) {
                (adapter as? AlbumsAdapter)?.initData(list)
            }
        }
    }
}