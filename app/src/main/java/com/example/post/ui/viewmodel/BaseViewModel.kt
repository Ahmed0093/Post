package com.example.post.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.post.ui.ViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow<ViewState<Any?>>(ViewState.ShowLoading)
    val uiState = _uiState.asStateFlow()

    val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("++Err", exception.message.toString())
        _uiState.value = ViewState.ErrorUiModel(exception.message ?: "Error")
    }

    suspend fun <T : Any> manageViewState(
        apiCall: suspend () -> T,
    ) {
        _uiState.value = ViewState.ShowLoading
        val result = apiCall.invoke()
        _uiState.value = ViewState.ResultUiModel(result)
    }

}




