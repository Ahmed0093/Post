package com.example.post.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.post.data.CoroutineDispatcher
import com.example.post.domain.model.PostDomainModel
import com.example.post.domain.useCase.GetAllPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

val MAX_ITEM_PER_PAGE = 10

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getAllPostsUseCase: GetAllPostsUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    var maxScreenVisible: Int = MAX_ITEM_PER_PAGE
    private val _uiState by lazy { mutableStateOf<HomeUiState>(HomeUiState.Loading) }
    internal val uiState: State<HomeUiState> by lazy { _uiState }

    private val _isLoadingNextPage by lazy { mutableStateOf(false) }
    internal val isLoadingNextPage: State<Boolean> by lazy { _isLoadingNextPage }

    private val _isPaginationExhaust by lazy { mutableStateOf(false) }
    internal val isPaginationExhaust: State<Boolean> by lazy { _isPaginationExhaust }

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("++Err Posts View model", exception.message.toString())
        _uiState.value = HomeUiState.Error
    }
    private val posts = mutableSetOf<PostDomainModel>()
    private var page = 1

    init {
        viewModelScope.launch(dispatcher.IO + handler) {
            startFromBegin()
        }
    }

    private fun startFromBegin() {
        page = 1
        posts.clear()
        loadUiState()
    }

    private fun loadUiState() {
        viewModelScope.launch(dispatcher.IO + handler) {
            showLoading()
            if (page == 1 || _isPaginationExhaust.value.not()) {
                val result = getAllPostsUseCase(page = page)
                delay(1000L)
                posts.addAll(result)
                if (posts.isEmpty()) {
                    _uiState.value = HomeUiState.Empty
                } else {
                    _isPaginationExhaust.value = result.count() != MAX_ITEM_PER_PAGE
                    _isLoadingNextPage.value = false
                    if (_isPaginationExhaust.value.not()) page++
                    Log.d("+++Result", "page count =${page}")

                    _uiState.value = HomeUiState.Success(posts = posts.toList())
                }
            }

        }
    }

    fun onFetchNextPage() {
        loadUiState()
    }

    fun onRetry() {
        loadUiState()
    }

    private fun showLoading() {
        if (page == 1) {
            _uiState.value = HomeUiState.Loading
        } else {
            _isLoadingNextPage.value = true
        }
    }
}

internal sealed class HomeUiState {
    data object Empty : HomeUiState()
    data object Error : HomeUiState()
    data object Loading : HomeUiState()
    data class Success(val posts: List<PostDomainModel>) : HomeUiState()
}

