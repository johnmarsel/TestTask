package com.johnmarsel.testtask.album

import androidx.lifecycle.*
import com.johnmarsel.testtask.ItunesRepository
import com.johnmarsel.testtask.Resource
import com.johnmarsel.testtask.model.ItunesItem
import kotlinx.coroutines.Dispatchers.IO

class AlbumListViewModel: ViewModel() {

    private val itunesRepository = ItunesRepository.get()
    val albumList: LiveData<Resource<List<ItunesItem>>>
    private val mutableSearchTerm = MutableLiveData<String>()

    init {
        albumList = mutableSearchTerm.switchMap { searchTerm ->
            liveData(context = viewModelScope.coroutineContext + IO) {
                emit(Resource.loading(data = null))
                try {
                    val result = itunesRepository.searchAlbums(searchTerm)
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

    fun searchAlbums(query: String = "") {
        mutableSearchTerm.value = query
    }
}

