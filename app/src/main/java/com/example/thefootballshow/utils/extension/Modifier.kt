package com.example.thefootballshow.utils.extension

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

fun Modifier.loadAsyncImage(
    url: String,
    context: Context,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Fit
): @Composable () -> Unit {
    return {
        AsyncImage(
            model = if (url.contains("svg")) {
                ImageRequest.Builder(context)
                    .data(url)
                    .decoderFactory(SvgDecoder.Factory())
                    .build()
            } else {
                url
            },
            modifier = this,
            contentDescription = contentDescription,
            contentScale = contentScale
        )
    }
}
