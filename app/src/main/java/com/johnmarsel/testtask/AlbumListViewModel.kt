package com.johnmarsel.testtask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johnmarsel.testtask.api.Album
import com.johnmarsel.testtask.api.ItunesApi
import kotlinx.coroutines.*

class AlbumListViewModel: ViewModel() {

    private var itunesApi = ItunesApi.get()

    private var job: Job? = null
    private var albumsList = MutableLiveData<List<Album>>()

    fun getAlbums() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = itunesApi.fetchAlbums("Metallica")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    albumsList.postValue(response.body()?.albums)
                }
            }
        }
    }
}