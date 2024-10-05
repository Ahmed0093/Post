package com.example.post.data.model

data class PostApiResponse(
    val data: List<PostDataModel>,
    val hitsPerPage: Int,
    val page: Int,
    val size: Int,
    val success: Boolean,
    val timeMs: Int,
    val totalHits: Int,
    val totalPages: Int
)

data class PostDataModel(
    val authors: List<String>,
    val contentLength: Int,
    val date: String,
    val excerpt: String,
    val keywords: List<String>,
    val language: String,
    val paywall: Boolean,
    val publisher: Publisher,
    val thumbnail: String,
    val title: String,
    val url: String
)

data class Publisher(
    val favicon: String,
    val name: String,
    val url: String
)