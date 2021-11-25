package com.johnmarsel.testtask.api

import com.google.gson.annotations.SerializedName

class ItunesResponse {
    @SerializedName("results")
    lateinit var albums: List<Album>
}