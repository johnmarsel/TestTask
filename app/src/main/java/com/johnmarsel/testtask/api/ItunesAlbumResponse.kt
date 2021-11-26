package com.johnmarsel.testtask.api

import com.google.gson.annotations.SerializedName

class ItunesAlbumResponse {
    @SerializedName("results")
    lateinit var albums: List<Album>
}