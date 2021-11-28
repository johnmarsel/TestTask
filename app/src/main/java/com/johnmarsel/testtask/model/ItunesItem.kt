package com.johnmarsel.testtask.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class ItunesItem (@PrimaryKey val collectionId: Int = 1,
                       val artistName: String = "",
                       val collectionName: String = "",
                       val releaseDate: Date = Date(),
                       val artworkUrl100: String = "",
                       val collectionType: String = "",
                       val trackCount: Int = 1,
                       val trackName: String = "",
                       val trackTimeMillis: Long = 1
)