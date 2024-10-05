package com.example.post.domain.useCase

import com.example.post.domain.model.PostDomainModel
import com.example.post.domain.repository.PostRepository
import com.example.post.ui.viewmodel.HomeUiState
import javax.inject.Inject

class GetAllPostsUseCase @Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke(page: Int): List<PostDomainModel> {
        return (repository.getPosts(page))
    }
}


