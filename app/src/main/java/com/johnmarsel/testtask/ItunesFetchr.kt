package com.johnmarsel.testtask

import com.johnmarsel.testtask.api.ItunesApi

class ItunesFetchr {

    private var itunesApi = ItunesApi.get()

    suspend fun getAlbums(term: String) = itunesApi.fetchAlbums(term)
}