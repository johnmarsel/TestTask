package com.johnmarsel.testtask

import com.johnmarsel.testtask.api.ItunesApi

class ItunesRepository {

    private var itunesApi = ItunesApi.get()

    suspend fun searchAlbums(term: String) = itunesApi.searchAlbums(term)
    suspend fun fetchSongs(collectionId: Int) = itunesApi.fetchSongs(collectionId)


    companion object {
        private var INSTANCE: ItunesRepository? = null

        fun initialize() {
            if (INSTANCE == null) {
                INSTANCE = ItunesRepository()
            }
        }

        fun get(): ItunesRepository {
            return INSTANCE ?:
            throw IllegalStateException("ItunesRepository must be initialized")
        }
    }
}