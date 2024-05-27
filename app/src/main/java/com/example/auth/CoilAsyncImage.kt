package com.example.auth

import android.graphics.Paint
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest

@Composable
fun CoilAsyncImage(
    model: String,
    contentDescription: String?,
    placeholder: Int,
    error: Int,
    modifier: Modifier = Modifier,
    contentScale: ContentScale,
) {

    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .respectCacheHeaders(false).build()

    val imageRequest = ImageRequest.Builder(context)
        .placeholder(placeholder)
        .error(error)
        .data(model)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()

    Image(
        painter = rememberAsyncImagePainter(imageRequest,imageLoader),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
    )
}