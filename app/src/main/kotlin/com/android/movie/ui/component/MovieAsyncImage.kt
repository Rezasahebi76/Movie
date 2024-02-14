package com.android.movie.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.android.movie.R

@Composable
fun MovieAsyncImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    val painter = rememberAsyncImagePainter(model = imageUrl)
    Box(
        modifier = modifier.clip(MaterialTheme.shapes.medium),
        contentAlignment = Alignment.Center,
    ) {

        if (painter.state is AsyncImagePainter.State.Error) {
            Icon(
                painter = painterResource(id = R.drawable.ic_broken_image),
                tint = MaterialTheme.colorScheme.surface,
                contentDescription = null
            )
        }

        if (painter.state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(modifier = Modifier.size(20.dp))
        }

        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painter,
            contentDescription = contentDescription,
        )
    }
}