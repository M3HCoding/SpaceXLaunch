package com.spacexlaunch.android.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.spacexlaunch.android.data.BookMarkModel
import com.spacexlaunch.android.data.XModel
import com.spacexlaunch.android.db.SpaceXDao
import com.spacexlaunch.android.network.SpaceXInterface
import com.spacexlaunch.android.utils.PagingSourceShips
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SpaceXRepository @Inject constructor(
    private val spaceXInterface: SpaceXInterface,
    private val spaceXDao: SpaceXDao
) {
    fun getShipsListing(query: String = "") = Pager(
        config = PagingConfig(10, maxSize = 100, enablePlaceholders = true),
        pagingSourceFactory = { PagingSourceShips(searchKey = query, spaceXDao = spaceXDao) }).flow

    suspend fun getShipDetails(flightNumber: Int): XModel? = withContext(Dispatchers.IO) {
        return@withContext spaceXDao.getShipDetails(flightNumber)
    }


    suspend fun loadFromApi(url: String) {
        val list = spaceXInterface.getShipList(url)
        list.body()?.let { spaceXDao.insertList(it) }
    }


    suspend fun getBookmark(flightNumber: Int): Int? = withContext(Dispatchers.IO) {
        return@withContext spaceXDao.getBookmark(flightNumber)
    }

    suspend fun deleteBookmark(flightNumber: Int) {
        spaceXDao.deleteBookmark(flightNumber)
    }

    suspend fun insertBookmark(item: BookMarkModel) {
        spaceXDao.insertBookmark(item)
    }

}