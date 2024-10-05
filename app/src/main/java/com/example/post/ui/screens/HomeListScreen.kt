package com.example.post.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.post.R
import com.example.post.domain.model.PostDomainModel
import com.example.post.ui.viewmodel.HomeUiState
import com.example.post.ui.viewmodel.PostsViewModel

const val TAG_STRING_BACK_BTN = "back_button"

@Composable
internal fun HomeListScreen(
    onSelectPost: (Long) -> Unit,
    postViewModel: PostsViewModel = hiltViewModel()
) {
    val uiState by postViewModel.uiState
    val isLoadingNextPage by postViewModel.isLoadingNextPage
    val isPaginationExhaust by postViewModel.isPaginationExhaust

    HomeContent(
        postViewModel,
        modifier = Modifier,
        uiState = uiState,
        isLoadingNextPage = isLoadingNextPage,
        isPaginationExhaust = isPaginationExhaust,
        onSelectPost = onSelectPost,

        onRetry = {
            postViewModel.onRetry()
        },
        onFetchNextPage = { postViewModel.onFetchNextPage() },

        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    homeViewModel: PostsViewModel,
    modifier: Modifier,
    uiState: HomeUiState,
    isLoadingNextPage: Boolean,
    isPaginationExhaust: Boolean,
    onSelectPost: (Long) -> Unit,
    onRetry: () -> Unit,
    onFetchNextPage: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.labelLarge,
                        text = stringResource(R.string.home_title),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            PostList(
                modifier = Modifier,
                uiState = uiState,
                isLoadingNextPage = isLoadingNextPage,
                isPaginationExhaust = isPaginationExhaust,
                onSelectPost = onSelectPost,
                onRetry = onRetry,
                onFetchNextPage = onFetchNextPage,
            )
        }
    }
}

@Composable
internal fun PostList(
    uiState: HomeUiState,
    isLoadingNextPage: Boolean,
    isPaginationExhaust: Boolean,
    onSelectPost: (Long) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    onFetchNextPage: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val configuration = LocalConfiguration.current
        when (uiState) {
            is HomeUiState.Empty -> {
                Text(stringResource(R.string.home_empty))
            }

            is HomeUiState.Error -> {
                Text(stringResource(R.string.home_retry_error))
                Button(onClick = { onRetry() }) {
                    Text(stringResource(R.string.home_retry))
                }
            }

            is HomeUiState.Loading -> {
                CircularProgressIndicator()
            }

            is HomeUiState.Success -> {
                val listState = rememberLazyListState()
                val endOfListReached by remember {
                    derivedStateOf { listState.lastScrolledForward }
                }
                LaunchedEffect(endOfListReached) {
                    if (endOfListReached && isLoadingNextPage.not() && isPaginationExhaust.not()) {
                        onFetchNextPage()
                    }
                }

                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    state = listState
                ) {
                    val screenHeight = configuration.screenHeightDp.dp - 100.dp
                    val itemHeight = screenHeight / 10
                    Log.d("++Resl", "itemHeigh =$itemHeight")
                    items(uiState.posts) { post ->
                        PostRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(itemHeight), post = post,
                            onSelectPost = { onSelectPost(post.id) }
                        )
                    }
                    if (isLoadingNextPage) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun PostRow(
    post: PostDomainModel,
    onSelectPost: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = { onSelectPost(post.id.toInt()) }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(start = 24.dp)
                .align(Alignment.CenterVertically)
        ) {
            CompositionLocalProvider(
                LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.38f
                )
            ) {
                Text(
                    modifier = Modifier.padding(top = 9.dp),
                    text = post.title,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
    HorizontalDivider(thickness = 4.dp)
}