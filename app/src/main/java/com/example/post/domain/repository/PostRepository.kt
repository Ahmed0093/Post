package com.example.post.domain.repository

import com.example.post.domain.model.PostDomainModel


interface PostRepository {
    suspend fun getPosts(page: Int = 1): List<PostDomainModel>
    suspend fun getPostById(postId: Long): PostDomainModel
}