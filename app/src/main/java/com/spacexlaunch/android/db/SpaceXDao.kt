package com.spacexlaunch.android.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spacexlaunch.android.data.XModel
import com.spacexlaunch.android.data.BookMarkModel
import kotlinx.coroutines.flow.Flow

@Dao
interface SpaceXDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<XModel>)

    @Query("SELECT * FROM x_model WHERE mission_name LIKE '%' || :searchKey || '%' ORDER BY flight_number ASC")
    fun getXModelsOrderedByFlightNumber(searchKey: String): Flow<List<XModel>>

    @Query("SELECT * FROM x_model WHERE  flight_number =:flightNumber")
    fun getShipDetails(flightNumber: Int): XModel?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(item: BookMarkModel)

    @Query("SELECT flight_number FROM bookmark_model WHERE  flight_number =:flightNumber")
    fun getBookmark(flightNumber: Int): Int?

    @Query("DELETE FROM bookmark_model WHERE flight_number = :bookmarkId")
    suspend fun deleteBookmark(bookmarkId: Int)
}