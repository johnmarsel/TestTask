package com.johnmarsel.testtask

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(
            status = Status.SUCCESS,
            data = data,
            message = null
        )

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(
                status = Status.ERROR,
                data = data,
                message = message
            )

        fun <T> loading(data: T?): Resource<T> = Resource(
            status = Status.LOADING,
            data = data,
            message = null
        )
    }
}

fun ImageView.loadImage(url: String) {
    val options = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.NONE)
    Glide.with(this)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}