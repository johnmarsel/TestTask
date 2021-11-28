package com.johnmarsel.testtask

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(url: String) {
    val options = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.NONE)
    Glide.with(this)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}