package com.example.post.data.datasource.remote

import com.example.post.data.NetworkRouter
import com.example.post.data.model.PostApiResponse
import javax.inject.Inject

class PostRemoteDataSourceImpl @Inject constructor(
    private val postApi: PostApi
) : PostRemoteDataSource {
    override suspend fun getPosts(page: Int): PostApiResponse {
        return NetworkRouter.invokeApi { postApi.getPosts(page = page) }
    }

}