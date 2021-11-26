package com.johnmarsel.testtask

import androidx.lifecycle.*
import com.johnmarsel.testtask.api.Album
import com.johnmarsel.testtask.api.ItunesApi
import com.johnmarsel.testtask.api.Song
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.Dispatcher

class AlbumListViewModel: ViewModel() {

    private var itunesFetchr = ItunesFetchr()
    private var itunesApi = ItunesApi.get()

    private var job: Job? = null
    lateinit var albumsList: LiveData<List<Album>>
    lateinit var songsList: LiveData<List<Song>>
    var mutableSearchTerm = MutableLiveData<String>()
    private lateinit var mySwitchLiveData: LiveData<List<Album>>

    init {
        albumsList = mutableSearchTerm.switchMap { searchTerm ->
            liveData(context = viewModelScope.coroutineContext + IO) {
                val result = itunesApi.fetchAlbums(searchTerm)
                result.body()?.let { emit(it.albums) }
            }
        }
    }

    fun getSongs(id: Int) {
        songsList = liveData(context = viewModelScope.coroutineContext + IO) {
            val result = itunesApi.fetchSongs(id)
            result.body()?.let { emit(it.songs) }
        }
    }


    fun fetchAlbums(query: String = "") {
        mutableSearchTerm.value = query
    }

}

