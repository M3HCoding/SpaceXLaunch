package com.spacexlaunch.android.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spacexlaunch.android.data.XModel
import com.spacexlaunch.android.repository.SpaceXRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class DetailedInformationViewModel @Inject constructor(private val repository: SpaceXRepository) :
    ViewModel() {
    suspend fun getDetails(flightNumber: Int): XModel? = viewModelScope.async {
        return@async repository.getShipDetails(flightNumber)
    }.await()
}