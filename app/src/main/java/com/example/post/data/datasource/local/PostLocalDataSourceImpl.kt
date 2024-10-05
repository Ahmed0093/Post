package com.example.post.data.datasource.local

import javax.inject.Inject

class PostLocalDataSourceImpl @Inject constructor(private val postDao: PostDao) : PostLocalDataSource {
    override suspend fun getAllPostsForPageNumber(page: String): List<Post> {
        return postDao.getAllPostsForPageNumber(page)
    }

    override suspend fun getPostById(posId: Long): Post {
        return postDao.getPostById(postId = posId)
    }

    override suspend fun insertPost(post: Post) {
        postDao.insertPost(post)
    }

}