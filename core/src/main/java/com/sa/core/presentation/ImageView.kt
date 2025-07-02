package com.sa.core.presentation

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.rememberAsyncImagePainter
import kotlin.let

@Composable
fun AppImage(
    modifier: Modifier,
    imageUrl: String,
    placeholder: Int? = null,
    contentScale: ContentScale = ContentScale.FillBounds
) {
    Image(
        painter = rememberAsyncImagePainter(
            model = imageUrl,
            placeholder = placeholder?.let { painterResource(id = it) }
        ),
        contentDescription = imageUrl,
        contentScale = contentScale,
        modifier = modifier
    )
}