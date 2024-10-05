package com.example.post.domain.useCase

import com.example.post.domain.model.PostDomainModel
import com.example.post.domain.repository.PostRepository
import javax.inject.Inject

class GetPostsByIdUseCase @Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke(postId: Long): PostDomainModel {
        return (repository.getPostById(postId))
    }
}


