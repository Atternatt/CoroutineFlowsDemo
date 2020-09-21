package com.m2f.sherpanytest.features.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.m2f.sherpanytest.R
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post
import com.m2f.sherpanytest.databinding.FragmentItemListBinding
import com.m2f.sherpanytest.di.viewmodel.ViewModelFactory
import com.m2f.sherpanytest.features.posts.model.PostUI
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * A Fragment representing a list of Pings. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ItemDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */

@AndroidEntryPoint
class ItemListFragment : Fragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val vm: PostsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(PostsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentItemListBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@ItemListFragment
                viewModel = vm

                //for a cold start we force to download the posts from server
                //vm.retrievePosts(forceRefresh = true)


            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView: RecyclerView = view.findViewById(R.id.item_list)
        val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)

        //todo @Marc -> search a better approach with data binding to handle clicks and actions like navigations.
        recyclerView.adapter = PostsAdapter(
            onItemSelected = { itemView, post ->
                val bundle = Bundle()
                bundle.putString(
                    ItemDetailFragment.ARG_ITEM_ID,
                    post.id.toLong().toString()
                )
                if (itemDetailFragmentContainer != null) {
                    itemDetailFragmentContainer.findNavController()
                        .navigate(R.id.fragment_item_detail, bundle)
                } else {
                    itemView.findNavController().navigate(R.id.show_item_detail, bundle)
                }
            },
            onItemRemoved = {
                vm.deletePostWithId(it.id)
            })

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            RecyclerView.VERTICAL
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

    }

    @BindingMethods(
        BindingMethod(
            type = SwipeRefreshLayout::class,
            attribute = "bind:onRefreshLayout",
            method = "setOnRefreshListener"
        )
    )
    companion object Bindings {

        @BindingAdapter("bind:posts")
        @JvmStatic
        fun RecyclerView.bindPosts(list: List<PostUI>?) {
            if (list != null) {
                (adapter as? PostsAdapter)?.initData(list)
            }
        }


        @BindingAdapter("bind:filter")
        @JvmStatic
        fun RecyclerView.filter(filter: String?) {
            if (filter != null) {
                (adapter as? PostsAdapter)?.filter?.filter(filter)
            }
        }
    }
}