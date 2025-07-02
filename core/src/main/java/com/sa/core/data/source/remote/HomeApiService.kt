package com.sa.core.data.source.remote

import com.sa.core.data.model.HomeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiService {

    //need to refactor base url it should be in here
    @GET("https://api-v2-b2sit6oh3a-uc.a.run.app/home_sections")
    suspend fun getHomeSections(
        @Query("page") page: Int
    ): HomeResponse

    @GET(" https://api-v2-b2sit6oh3a-uc.a.run.app/home_sections?")
    suspend fun sectionSearch(
        @Query("page") page: Int, @Query("name") query: String?
    ): HomeResponse
}