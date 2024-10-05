package com.example.post.data.repository

import android.util.Log
import com.example.post.data.datasource.local.PostLocalDataSource
import com.example.post.data.datasource.remote.PostRemoteDataSource
import com.example.post.data.mapPostResponseModelToDomain
import com.example.post.data.toPostDomainModel
import com.example.post.data.toPostModel
import com.example.post.domain.BaseException
import com.example.post.domain.model.PostDomainModel
import com.example.post.domain.repository.PostRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsRepositoryImpl @Inject constructor(
    private val postLocalDataSource: PostLocalDataSource,
    private val postRemDataSource: PostRemoteDataSource,
) : PostRepository {
    override suspend fun getPosts(page: Int): List<PostDomainModel> {
        var posts = ArrayList<PostDomainModel>()
        try {
            val localPosts = postLocalDataSource.getAllPostsForPageNumber(page.toString())
            if (localPosts.isEmpty()) {
                val result = postRemDataSource.getPosts(page = page)
                result.data.forEach {
                    postLocalDataSource.insertPost(it.toPostModel(page))
                }
                posts = mapPostResponseModelToDomain(result)
            } else {
                localPosts.forEach {
                    posts.add(it.toPostDomainModel())
                }
            }
        } catch (e: BaseException) {
            Log.e("++++++ERR", e.message.toString())
            return posts
        }

        return posts
    }

    override suspend fun getPostById(postId: Long): PostDomainModel {
        return postLocalDataSource.getPostById(postId).toPostDomainModel()
    }

}