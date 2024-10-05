package com.example.post.data.datasource.remote

import com.example.post.data.model.PostApiResponse
import com.example.post.domain.HEADER_API_KEY
import com.example.post.domain.HEADER_API_VALUE
import com.example.post.domain.HEADER_HOST_KEY
import com.example.post.domain.HEADER_HOST_VALUE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PostApi {
    @GET("search/articles?query=cats&language=en")
    suspend fun getPosts(
        @Header(HEADER_API_KEY) key: String? = HEADER_API_VALUE,
        @Header(HEADER_HOST_KEY) host: String? = HEADER_HOST_VALUE,
        @Query("page") page: Int = 1
    ): Response<PostApiResponse>

}