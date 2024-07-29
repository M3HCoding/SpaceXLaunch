package com.spacexlaunch.android.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spacexlaunch.android.data.BookMarkModel
import com.spacexlaunch.android.repository.SpaceXRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val spaceXRepository: SpaceXRepository):ViewModel() {
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    init {
        viewModelScope.launch {
            _loadingState.value = true
            val url = "https://api.spacexdata.com/v3/launches"
            spaceXRepository.loadFromApi(url)
            _loadingState.value = false
        }
    }

    fun getMoviesListing(query:String="") = spaceXRepository.getShipsListing(query)

    suspend fun getBookmark(flightNumber:Int): Int?= viewModelScope.async {
        return@async spaceXRepository.getBookmark(flightNumber)
    }.await()

    suspend fun deleteBookmark(flightNumber:Int){
        spaceXRepository.deleteBookmark(flightNumber)

    }

    suspend fun insertBookmark(item: BookMarkModel){
        spaceXRepository.insertBookmark(item)
    }

}