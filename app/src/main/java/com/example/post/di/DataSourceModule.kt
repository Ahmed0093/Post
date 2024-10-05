package com.example.post.di

import com.example.post.data.datasource.local.PostLocalDataSource
import com.example.post.data.datasource.local.PostLocalDataSourceImpl
import com.example.post.data.datasource.remote.PostRemoteDataSource
import com.example.post.data.datasource.remote.PostRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal interface DataSourceModuleBindings {

    @Binds
    @Singleton
    fun bindPostLocalDataSource(impl: PostLocalDataSourceImpl): PostLocalDataSource

    @Binds
    @Singleton
    fun bindPostRemoteDataSource(impl: PostRemoteDataSourceImpl): PostRemoteDataSource
}