package ru.skillbranch.gameofthrones.data.remote

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

interface HouseApi {

    @GET("houses")
    fun getHousesByPage(@Query("page") page: Int = 1, @Query("pageSize") pageSize: Int = 50): Observable<Response<List<HouseRes>>>

    @GET("houses")
    fun getHousesByName(@Query("name") name: String): Observable<List<HouseRes>>

    @GET("characters/{id}")
    fun getCharacterById(@Path("id") id: String): Observable<CharacterRes>
}