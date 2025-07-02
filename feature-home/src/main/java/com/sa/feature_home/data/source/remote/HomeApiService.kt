package com.sa.feature_home.data.source.remote

 import com.sa.feature_home.data.model.HomeResponse
 import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiService {
    @GET("https://api-v2-b2sit6oh3a-uc.a.run.app/home_sections")
    suspend fun getHomeSections(
        @Query("page") page: Int
    ): HomeResponse
}