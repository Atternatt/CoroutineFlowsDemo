package com.m2f.sherpanytest.features.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.m2f.sherpanytest.R
import com.m2f.sherpanytest.databinding.RowAlbumBinding
import com.m2f.sherpanytest.features.posts.model.AlbumUI

class AlbumsAdapter :
    RecyclerView.Adapter<AlbumsAdapter.AlbumUIViewholder>(), Filterable {

    private var originalData: List<AlbumUI> = emptyList()

    private var data: List<AlbumUI> = emptyList()
        set(newList) {
            val calculateDiff = DiffUtil.calculateDiff(AlbumUIDiffCallback(field, newList))
            calculateDiff.dispatchUpdatesTo(this)
            field = newList
        }

    fun initData(list: List<AlbumUI>) {
        originalData = list
        data = list
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumUIViewholder {
        return AlbumUIViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_album, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AlbumUIViewholder, position: Int) {
        holder.bind(data[position])
    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filterAlbumUI: String = constraint.toString().toLowerCase()

            val results = FilterResults()

            val list: List<AlbumUI> = originalData

            val count = list.size
            val nlist = mutableListOf<AlbumUI>()

            var filterableAlbumUI: AlbumUI

            for (i in 0 until count) {
                filterableAlbumUI = list[i]
                if (filterableAlbumUI.title.toLowerCase().contains(filterAlbumUI)) {
                    nlist.add(filterableAlbumUI)
                }
            }

            results.values = nlist
            results.count = nlist.size

            return results
        }

        override fun publishResults(constraint: CharSequence, result: FilterResults) {
            data = result.values as List<AlbumUI>
        }

    }

    class AlbumUIViewholder(containerView: View) :
        RecyclerView.ViewHolder(containerView) {

        private val binding = RowAlbumBinding.bind(containerView)

        fun bind(item: AlbumUI) {
            binding.albumUI = item

            if(binding.photos.adapter == null) {
                binding.photos.adapter = PhotoAdapter(item.photos)
            }

            binding.executePendingBindings()
            itemView.setOnClickListener {
                if(binding.motionContent.progress >= 0.9) {
                    binding.motionContent.transitionToStart()
                } else {
                    binding.motionContent.transitionToEnd()
                }
            }
        }
    }

}

class AlbumUIDiffCallback(val oldList: List<AlbumUI>, val newList: List<AlbumUI>) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.id == new.id &&
                old.title == new.title &&
                old.photos.size == new.photos.size
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }
}