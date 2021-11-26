package com.johnmarsel.testtask.api

import java.util.*

data class Album (var artistName: String = "",
                  var collectionName: String = "",
                  var releaseDate: Date = Date(),
                  var artworkUrl60: String = "",
                  var collectionId: Int = 1
)