package com.android.movie.presentation.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.android.movie.R
import com.android.movie.common.getErrorTitleId
import com.android.movie.models.Movie
import com.android.movie.ui.component.MovieAsyncImage
import com.android.movie.ui.component.MovieBorderButton

@Composable
internal fun MovieLazyVerticalGrid(
    modifier: Modifier,
    pagingItems: LazyPagingItems<Movie>,
    imageBaseUrl: String,
    gridState: LazyGridState,
    columnCount: Int,
    verticalArrangement: Arrangement.Vertical,
    horizontalArrangement: Arrangement.Horizontal,
    onMovieClick: (Movie) -> Unit,
    onRetryClick: () -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        state = gridState,
        columns = GridCells.Fixed(columnCount),
        contentPadding = PaddingValues(start = 10.dp, end = 10.dp, top = 52.dp, bottom = 22.dp),
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement
    ) {
        items(pagingItems.itemCount, pagingItems.itemKey { it.id }) {
            pagingItems[it]?.let { movie ->
                Movie(
                    movie = movie,
                    imageBaseUrl = imageBaseUrl,
                    onMovieClick = onMovieClick
                )
            }
        }
        if (pagingItems.loadState.refresh is LoadState.Loading || pagingItems.loadState.append is LoadState.Loading) {
            item(span = { GridItemSpan(columnCount) }) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(bottom = 22.dp)
                            .size(32.dp)
                    )
                }
            }
        } else if (pagingItems.loadState.refresh is LoadState.Error || pagingItems.loadState.append is LoadState.Error) {
            item(span = { GridItemSpan(columnCount) }) {
                ErrorItem(
                    errorTitle = stringResource(
                        id = pagingItems.loadState.getErrorTitleId()
                            ?: R.string.something_went_wrong
                    ),
                    onRetryClick = onRetryClick
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Movie(
    movie: Movie,
    imageBaseUrl: String,
    onMovieClick: (Movie) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMovieClick(movie) }
    ) {
        MovieAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(.7f),
            imageUrl = imageBaseUrl + movie.posterPath,
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .basicMarquee()
                .padding(top = 16.dp),
            textAlign = TextAlign.Center,
            text = movie.title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun ErrorItem(
    errorTitle: String,
    onRetryClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            text = errorTitle,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1
        )
        MovieBorderButton(
            modifier = Modifier
                .padding(start = 10.dp),
            onClick = onRetryClick
        ) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}
