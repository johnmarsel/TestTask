package com.johnmarsel.testtask.api

import com.google.gson.annotations.SerializedName

class ItunesSongResponse {
    @SerializedName("results")
    lateinit var songs: List<Song>
}