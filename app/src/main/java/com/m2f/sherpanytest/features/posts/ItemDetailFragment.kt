package com.m2f.sherpanytest.features.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.m2f.sherpanytest.databinding.FragmentItemDetailBinding
import com.m2f.sherpanytest.di.viewmodel.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ItemDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val vm: PostDetailViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(PostDetailViewModel::class.java)
    }

    private val postId: Long by lazy { arguments?.getString(ARG_ITEM_ID)?.toLong() ?: -1 }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentItemDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@ItemDetailFragment
            viewModel = vm
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(postId >= 0) {
            vm.loadPostDetail(postId)
        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}