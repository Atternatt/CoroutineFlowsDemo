package com.m2f.sherpanytest.features.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.m2f.sherpanytest.R
import com.m2f.sherpanytest.databinding.RowPostBinding
import com.m2f.sherpanytest.features.posts.model.PostUI

class PostsAdapter(
    private val onItemSelected: (View, PostUI) -> Unit,
    private val onItemRemoved: (PostUI) -> Unit
) :
    RecyclerView.Adapter<PostsAdapter.PostUIViewholder>(), Filterable {

    private var originalData: List<PostUI> = emptyList()

    private var data: List<PostUI> = emptyList()
        set(newList) {
            val calculateDiff = DiffUtil.calculateDiff(PostUIDiffCallback(field, newList))
            calculateDiff.dispatchUpdatesTo(this)
            field = newList
        }

    fun initData(list: List<PostUI>) {
        originalData = list
        data = list
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostUIViewholder {
        return PostUIViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_post, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostUIViewholder, position: Int) {
        holder.bind(data[position])
    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filterPostUI: String = constraint.toString().toLowerCase()

            val results = FilterResults()

            val list: List<PostUI> = originalData

            val count = list.size
            val nlist = mutableListOf<PostUI>()

            var filterablePostUI: PostUI

            for (i in 0 until count) {
                filterablePostUI = list[i]
                if (filterablePostUI.title.toLowerCase().contains(filterPostUI)) {
                    nlist.add(filterablePostUI)
                }
            }

            results.values = nlist
            results.count = nlist.size

            return results
        }

        override fun publishResults(constraint: CharSequence, result: FilterResults) {
            data = result.values as List<PostUI>
        }

    }

    inner class PostUIViewholder(containerView: View) :
        RecyclerView.ViewHolder(containerView) {

        private val binding: RowPostBinding = RowPostBinding.bind(containerView)

        fun bind(item: PostUI) {
            itemView.setOnClickListener {
                onItemSelected(it, item)
            }
            binding.post = item
            binding.ivDelete.setOnClickListener {
                onItemRemoved(item)
            }
            binding.executePendingBindings()
        }
    }

}

class PostUIDiffCallback(private val oldList: List<PostUI>, private val newList: List<PostUI>) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.id == new.id &&
                old.title == new.title &&
                old.userEmail == old.userEmail
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }
}