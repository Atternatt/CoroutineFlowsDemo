package com.m2f.sherpanytest.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


@BindingAdapter("bind:image")
fun ImageView.loadImage(image: String?) {
    Picasso.get().load(image).into(this)
}