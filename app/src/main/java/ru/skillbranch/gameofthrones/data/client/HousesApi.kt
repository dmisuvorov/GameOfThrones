package ru.skillbranch.gameofthrones.data.client

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

interface HousesApi {

    @GET("/houses")
    fun getHousesByPage(@Query("page") page: Int, @Query("pageSize") pageSize: Int): Single<HouseRes>
}