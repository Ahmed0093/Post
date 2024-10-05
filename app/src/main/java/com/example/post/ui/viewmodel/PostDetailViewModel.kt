package com.example.post.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.post.data.CoroutineDispatcher
import com.example.post.domain.useCase.GetPostsByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val useCase: GetPostsByIdUseCase
) : BaseViewModel() {

    suspend fun getPostById(postId: String) {
        viewModelScope.launch(dispatcher.IO + handler) {
            manageViewState {
                useCase.invoke(postId.toLong())
            }
        }
    }

}