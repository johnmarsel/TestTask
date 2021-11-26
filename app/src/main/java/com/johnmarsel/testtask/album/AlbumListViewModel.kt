package com.johnmarsel.testtask.album

import androidx.lifecycle.*
import com.johnmarsel.testtask.ItunesRepository
import com.johnmarsel.testtask.api.Album
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class AlbumListViewModel: ViewModel() {

    private var itunesRepository = ItunesRepository.get()

    private var job: Job? = null
    var albumsList: LiveData<List<Album>>

    var mutableSearchTerm = MutableLiveData<String>()
    private lateinit var mySwitchLiveData: LiveData<List<Album>>

    init {
        albumsList = mutableSearchTerm.switchMap { searchTerm ->
            liveData(context = viewModelScope.coroutineContext + IO) {
                val result = itunesRepository.searchAlbums(searchTerm)
                result.body()?.let { emit(it.albums) }
            }
        }
    }

    fun searchAlbums(query: String = "") {
        mutableSearchTerm.value = query
    }

}

