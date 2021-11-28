package com.johnmarsel.testtask

import android.content.Context
import androidx.room.Room
import com.johnmarsel.testtask.api.ItunesApi
import com.johnmarsel.testtask.database.ItunesDataBase
import com.johnmarsel.testtask.model.ItunesItem

private const val DATABASE_NAME = "itunes-database"

class ItunesRepository private constructor(context: Context) {

    private val database : ItunesDataBase = Room.databaseBuilder(
        context.applicationContext,
        ItunesDataBase::class.java,
        DATABASE_NAME
    ).build()

    private val crimeDao = database.itunesDao()
    private val itunesApi = ItunesApi.get()

    fun fetchLocalItems(): List<ItunesItem> {
        return crimeDao.getItunesItems()
    }

    fun insertLocalItems(items: List<ItunesItem>) = crimeDao.insertItunesItems(items)
    fun deleteLocalItems() = crimeDao.deleteItunesItems()

    suspend fun searchItems(term: String) = itunesApi.searchAlbums(term)
    suspend fun fetchItems(collectionId: Int) = itunesApi.fetchSongs(collectionId)

    companion object {
        private var INSTANCE: ItunesRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ItunesRepository(context)
            }
        }

        fun get(): ItunesRepository {
            return INSTANCE ?:
            throw IllegalStateException("ItunesRepository must be initialized")
        }
    }
}