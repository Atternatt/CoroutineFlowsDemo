package com.m2f.sherpanytest.features.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.m2f.sherpanytest.R
import com.m2f.sherpanytest.databinding.RowPhotoBinding


class PhotoAdapter(private val images: List<String>) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_photo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = RowPhotoBinding.bind(itemView)

        fun bind(item: String) {
            binding.photo = item
            binding.executePendingBindings()
        }
    }
}