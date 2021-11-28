package com.johnmarsel.testtask.model

import com.google.gson.annotations.SerializedName

class ItunesResponse {
    @SerializedName("results")
    lateinit var items: List<ItunesItem>
}