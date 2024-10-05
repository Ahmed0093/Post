package com.example.post.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.post.ui.AppBar
import com.example.post.ui.CircleLoaderUI
import com.example.post.ui.ErrorUI
import com.example.post.ui.viewmodel.PostDetailViewModel
import com.example.post.R
import com.example.post.domain.model.PostDomainModel
import com.example.post.ui.Screen
import com.example.post.ui.ViewState

@Composable
fun DetailScreen(navController: NavController, postId: String?) {

    val postDetailViewModel: PostDetailViewModel = hiltViewModel()
    val uiState by postDetailViewModel.uiState.collectAsState()


    AppBar(
        title = stringResource(R.string.post_detail_screen),
        onLeadingIconClick = {
            navController.navigate(Screen.List.name) {
                popUpTo(Screen.List.name) {
                    inclusive = true

                }
            }
        },
        trailingIcon = {
            Text(
                text = stringResource(R.string.refresh),
                color = Color.Blue, modifier = Modifier.padding(top = 15.dp)
            )
        }, onTrailingIconClick = {
        })
    Spacer(modifier = Modifier.height(16.dp))
    LaunchedEffect(Unit) {
        postDetailViewModel.getPostById(postId = postId.toString())

    }
    when (uiState) {

        is ViewState.ShowLoading -> {
            CircleLoaderUI()

        }

        is ViewState.ResultUiModel -> {
            val postItem =
                (uiState as ViewState.ResultUiModel<Any?>).uiModel as PostDomainModel
            DetailItemScreen(postItem)
        }

        is ViewState.ErrorUiModel -> {
            ErrorUI {

            }

        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun DetailItemScreen(post: PostDomainModel) {
    Column(
        modifier = Modifier
            .padding(start = 15.dp, top = 100.dp, end = 15.dp)
    ) {

        Text(
            text = post.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary,
        )

        Image(
            painter = rememberAsyncImagePainter(post.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(top = 10.dp)
        )
    }
    HorizontalDivider(thickness = 4.dp)
}

