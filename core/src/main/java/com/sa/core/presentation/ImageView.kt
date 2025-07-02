package com.sa.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import kotlin.let

@Composable
fun DefaultImage(
    modifier: Modifier,
    imageUrl: String,
    placeholder: Int? = null,
    contentScale: ContentScale = ContentScale.FillBounds
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale,
        placeholder = placeholder?.let { painterResource(id = it) }
    )
}