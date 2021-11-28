package com.johnmarsel.testtask.model

import java.util.*

data class ItunesItem (var artistName: String = "",
                       var collectionName: String = "",
                       var releaseDate: Date = Date(),
                       var artworkUrl100: String = "",
                       var collectionType: String = "",
                       var collectionId: Int = 1,
                       var trackCount: Int = 1,
                       var trackName: String = "",
                       var trackTimeMillis: Long = 1
)