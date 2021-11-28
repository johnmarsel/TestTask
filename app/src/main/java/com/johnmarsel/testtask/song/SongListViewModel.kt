package com.johnmarsel.testtask.song

import androidx.lifecycle.*
import com.johnmarsel.testtask.ItunesRepository
import com.johnmarsel.testtask.Resource
import com.johnmarsel.testtask.model.ItunesItem
import kotlinx.coroutines.Dispatchers

class SongListViewModel: ViewModel() {

    private val itunesRepository = ItunesRepository.get()
    val songsList: LiveData<Resource<List<ItunesItem>>>
    private val mutableCollectionId = MutableLiveData<Int>()

    init {
        songsList = mutableCollectionId.switchMap { collectionId ->
            liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                emit(Resource.loading(data = null))
                try {
                    val result = itunesRepository.fetchSongs(collectionId)
                    emit(Resource.success(data = result.body()!!.items))
                } catch (exception: Exception) {
                    emit(
                        Resource.error(
                            data = null,
                            message = "Network error"
                        )
                    )
                }
            }
        }
    }

    fun fetchSongs(collectionId: Int) {
        mutableCollectionId.value = collectionId
    }
}