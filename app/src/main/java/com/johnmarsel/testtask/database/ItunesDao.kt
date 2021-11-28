package com.johnmarsel.testtask.database

import androidx.room.*
import com.johnmarsel.testtask.model.ItunesItem

@Dao
interface ItunesDao {
    @Query("SELECT * FROM ItunesItem")
    fun getItunesItems(): List<ItunesItem>

    @Insert
    fun insertItunesItems(items: List<ItunesItem>)

    @Query("DELETE FROM ItunesItem")
    fun deleteItunesItems()
}