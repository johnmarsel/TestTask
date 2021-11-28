package com.johnmarsel.testtask.album

import android.app.Application
import androidx.lifecycle.*
import com.johnmarsel.testtask.ItunesRepository
import com.johnmarsel.testtask.QueryPreferences
import com.johnmarsel.testtask.Resource
import com.johnmarsel.testtask.model.ItunesItem
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AlbumListViewModel(private val app: Application): AndroidViewModel(app) {

    private val itunesRepository = ItunesRepository.get()
    var albumList: LiveData<Resource<List<ItunesItem>>>
    private val mutableSearchTerm = MutableLiveData<String>()

    init {
        albumList = mutableSearchTerm.switchMap { searchTerm ->
            liveData(context = viewModelScope.coroutineContext + IO) {
                emit(Resource.loading(data = null))
                try {
                    val result = itunesRepository.searchItems(searchTerm)
                    // delete old entries
                    itunesRepository.deleteLocalItems()
                    // add new last entries to Room
                    itunesRepository.insertLocalItems(result.body()!!.items)
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

    fun restoreAlbums() {
        viewModelScope.launch(IO) {
            val response = Resource.success(data = itunesRepository.fetchLocalItems())
            (albumList as MutableLiveData).postValue(response)
        }
    }

    fun searchAlbums(query: String = "") {
        QueryPreferences.setLastStoredQuery(app, query)
        mutableSearchTerm.value = query
    }
}

