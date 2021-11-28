package com.johnmarsel.testtask.song

import androidx.lifecycle.*
import com.johnmarsel.testtask.ItunesRepository
import com.johnmarsel.testtask.model.ItunesItem
import kotlinx.coroutines.Dispatchers

class SongListViewModel: ViewModel() {

    private val itunesRepository = ItunesRepository.get()
    val songsList: LiveData<List<ItunesItem>>
    private val mutableCollectionId = MutableLiveData<Int>()

    init {
        songsList = mutableCollectionId.switchMap { collectionId ->
            liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                val result = itunesRepository.fetchSongs(collectionId)
                result.body()?.let { emit(it.items) }
            }
        }
    }

    fun fetchSongs(collectionId: Int) {
        mutableCollectionId.value = collectionId
    }
}