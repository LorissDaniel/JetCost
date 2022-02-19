package it.lorisdaniel.jetcosttest.api

import androidx.lifecycle.LiveData
import it.lorisdaniel.jetcosttest.model.GoogleSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleSearch {

    @GET("v1?key=AIzaSyBBzTTw6hjVZomR4CQupPvHRbWyLRiEYOc&cx=7262c066d691b4ca7&searchType=image")
    suspend fun getImages(
        @Query("q") query: String,
        @Query("start") start: Int
    ): GoogleSearchResponse

}