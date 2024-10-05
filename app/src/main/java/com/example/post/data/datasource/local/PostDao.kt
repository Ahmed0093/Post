package com.example.post.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface PostDao {
    @Query("SELECT * FROM posts")
    fun getAllPosts(): List<Post>

    @Query("SELECT * FROM posts where pageNumber = :number")
    fun getAllPostsForPageNumber(number: String): List<Post>

    @Query("SELECT * FROM posts  ORDER BY id ASC LIMIT :limitPerPage OFFSET :offset")
    fun getAllPostsForPageNumber2(offset: Int, limitPerPage: Int): List<Post>

    @Query("SELECT * FROM posts WHERE id LIKE :postId")
    fun getPostById(postId: Long): Post

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: Post)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(post: List<Post>)

    @Query("DELETE FROM posts")
    fun clearAll()
}