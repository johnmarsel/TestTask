package com.johnmarsel.testtask.song

import androidx.lifecycle.*
import com.johnmarsel.testtask.ItunesRepository
import com.johnmarsel.testtask.api.Song
import kotlinx.coroutines.Dispatchers

class SongListViewModel: ViewModel() {

    private var itunesRepository = ItunesRepository.get()
    var songsList: LiveData<List<Song>>
    private val collectionIdLiveData = MutableLiveData<Int>()

    init {
        songsList = collectionIdLiveData.switchMap { collectionId ->
            liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                val result = itunesRepository.fetchSongs(collectionId)
                result.body()?.let { emit(it.songs) }
            }
        }
    }

    fun fetchSongs(collectionId: Int) {
        collectionIdLiveData.value = collectionId
    }
}