package com.example.post.di

import android.content.Context
import androidx.room.Room
import com.example.post.data.datasource.local.AppDatabase
import com.example.post.data.datasource.local.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "post_database"
        ).build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): PostDao {
        return database.postDao()
    }
}


