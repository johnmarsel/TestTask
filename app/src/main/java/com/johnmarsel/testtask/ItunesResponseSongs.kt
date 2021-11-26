package com.johnmarsel.testtask

import com.google.gson.annotations.SerializedName
import com.johnmarsel.testtask.api.Song

class ItunesResponseSongs {
    @SerializedName("results")
    lateinit var songs: List<Song>
}