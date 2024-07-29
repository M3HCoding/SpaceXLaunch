package com.spacexlaunch.android.network

import com.spacexlaunch.android.data.XModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface SpaceXInterface {
    @GET
    suspend fun getShipList(@Url url:String): Response<List<XModel>>
}