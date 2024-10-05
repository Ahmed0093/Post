package com.example.post.data.datasource.remote

import com.example.post.data.model.PostApiResponse

interface PostRemoteDataSource {
    suspend fun getPosts(page: Int): PostApiResponse
}