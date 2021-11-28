package com.johnmarsel.testtask.album

import androidx.lifecycle.*
import com.johnmarsel.testtask.ItunesRepository
import com.johnmarsel.testtask.model.ItunesItem
import kotlinx.coroutines.Dispatchers.IO

class AlbumListViewModel: ViewModel() {

    private val itunesRepository = ItunesRepository.get()
    val albumList: LiveData<List<ItunesItem>>
    private val mutableSearchTerm = MutableLiveData<String>()

    init {
        albumList = mutableSearchTerm.switchMap { searchTerm ->
            liveData(context = viewModelScope.coroutineContext + IO) {
                val result = itunesRepository.searchAlbums(searchTerm)
                result.body()?.let { emit(it.items) }
            }
        }
    }

    fun searchAlbums(query: String = "") {
        mutableSearchTerm.value = query
    }

}

