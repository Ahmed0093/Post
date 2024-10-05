package com.example.post.data.datasource.local

interface PostLocalDataSource {
    suspend fun getAllPostsForPageNumber(page: String): List<Post>
    suspend fun getPostById(posId: Long): Post
    suspend fun insertPost(post: Post)
}