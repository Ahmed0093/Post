package com.example.post.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.post.R
import com.example.post.ui.screens.TAG_STRING_BACK_BTN

@Composable
fun AppBar(
    title: String,
    makeTitleUppercase: Boolean = false,
    titleTag: String = "",
    leadingIconTag: String = TAG_STRING_BACK_BTN,
    style: TextStyle = MaterialTheme.typography.headlineSmall,
    onLeadingIconClick: () -> Unit = {},
    onTrailingIconClick: () -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = {
        Image(
            painter = painterResource(id = R.drawable.ic_back_v1),
            contentDescription = "back",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Inside
        )
    },
    trailingIcon: @Composable (() -> Unit)? = {}
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(top = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(Modifier
            .weight(0.15f)
            .aspectRatio(1f)
            .testTag(leadingIconTag)
            .clickable(enabled = leadingIcon != null) {
                onLeadingIconClick()
            }
            .fillMaxHeight()
        ) {
            leadingIcon?.let { it() }
        }

        Text(
            text = if (makeTitleUppercase) title.uppercase() else title,
            modifier = Modifier
                .testTag(titleTag)
                .weight(0.7f)
                .align(Alignment.CenterVertically),
            style = style,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Box(
            modifier = Modifier
                .weight(0.15f)
                .aspectRatio(1f)
                .clickable(enabled = trailingIcon != null) {
                    onTrailingIconClick()
                }
                .fillMaxHeight(),

            ) {
            trailingIcon?.let { it() }
        }
    }
}

@Composable
fun CircleLoaderUI() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorUI(onRefreshClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(R.string.error_text))
        Button(onClick = {
            onRefreshClick()
        }) {
            Text(text = stringResource(R.string.refresh))
        }
    }
}