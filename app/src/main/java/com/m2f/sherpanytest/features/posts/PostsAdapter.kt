package com.m2f.sherpanytest.features.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.m2f.sherpanytest.R
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post
import com.m2f.sherpanytest.databinding.RowPostBinding

class PostsAdapter(private val onItemSelected: (View, Post) -> Unit) :
    RecyclerView.Adapter<PostsAdapter.PostViewholder>(), Filterable {

    private var originalData: List<Post> = emptyList()

    private var data: List<Post> = emptyList()
        set(newList) {
            val calculateDiff = DiffUtil.calculateDiff(PostDiffCallback(field, newList))
            calculateDiff.dispatchUpdatesTo(this)
            field = newList
        }

    fun initData(list: List<Post>) {
        originalData = list
        data = list
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewholder {
        return PostViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_post, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostViewholder, position: Int) {
        holder.bind(data[position])
    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filterPost: String = constraint.toString().toLowerCase()

            val results = FilterResults()

            val list: List<Post> = originalData

            val count = list.size
            val nlist = mutableListOf<Post>()

            var filterablePost: Post

            for (i in 0 until count) {
                filterablePost = list[i]
                if (filterablePost.title.toLowerCase().contains(filterPost)) {
                    nlist.add(filterablePost)
                }
            }

            results.values = nlist
            results.count = nlist.size

            return results
        }

        override fun publishResults(constraint: CharSequence, result: FilterResults) {
            data = result.values as List<Post>
        }

    }

    inner class PostViewholder(containerView: View) :
        RecyclerView.ViewHolder(containerView) {
        fun bind(item: Post) {
            itemView.setOnClickListener {
                onItemSelected(it, item)
            }
            RowPostBinding.bind(itemView).apply {
                post = item
                executePendingBindings()
            }
        }
    }

}

class PostDiffCallback(val oldList: List<Post>, val newList: List<Post>) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        //todo: add all comparable params.
        return old.id == new.id
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }
}