package com.spacexlaunch.android.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.spacexlaunch.android.data.XModel
import com.spacexlaunch.android.db.SpaceXDao
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PagingSourceShips @Inject constructor(
    private val spaceXDao: SpaceXDao,
    private val searchKey: String
) : PagingSource<Int, XModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, XModel> {
        return try {
            // Load data from Room, ordered by flight_number
            val data = spaceXDao.getXModelsOrderedByFlightNumber(searchKey).first()
            LoadResult.Page(
                data = data,
                prevKey = null, // No previous page when loading from Room
                nextKey = null  // No next page when loading from Room
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, XModel>): Int? {
        // Not needed when loading from Room as there's no pagination
        return null
    }
}