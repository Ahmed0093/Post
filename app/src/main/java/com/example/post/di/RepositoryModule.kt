package com.example.post.di

import com.example.post.data.repository.PostsRepositoryImpl
import com.example.post.domain.repository.PostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPostRepository(
        postsRepositoryImpl: PostsRepositoryImpl
    ): PostRepository

}