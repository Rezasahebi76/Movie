package com.android.movie.presentation.home

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.android.movie.R
import com.android.movie.common.getErrorDescriptionId
import com.android.movie.common.getErrorTitleId
import com.android.movie.common.result.Result
import com.android.movie.models.Movie
import com.android.movie.ui.component.MovieAppBar
import com.android.movie.ui.component.MovieAsyncImage
import com.android.movie.ui.component.MovieBorderButton
import com.android.movie.ui.component.MovieButton
import com.android.movie.ui.theme.GradientEnd
import com.android.movie.ui.theme.GradientStart
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val imageUrlResult by viewModel.imageBaseUrl.collectAsStateWithLifecycle()

    val lazyPagingItems = viewModel.pager.collectAsLazyPagingItems()

    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            if (imageUrlResult is Result.Success) {
                MovieAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    title = {
                        Text(stringResource(id = R.string.discover))
                    },
                    actions = {
                        Icon(
                            modifier = Modifier.size(38.dp),
                            painter = painterResource(id = R.drawable.bazaar_logo),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                )
            }
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            HomeScreen(
                pagingItems = lazyPagingItems,
                imageBaseUrlResult = imageUrlResult,
                onRetryClick = {
                    if (imageUrlResult is Result.Error) {
                        viewModel.getImageUrl()
                    }
                    if (lazyPagingItems.loadState.refresh is LoadState.Error ||
                        lazyPagingItems.loadState.append is LoadState.Error
                    ) {
                        lazyPagingItems.retry()
                    }
                },
                onMovieClick = {
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(it.title)
                    }
                }
            )
        }
    }
}

@Composable
private fun HomeScreen(
    pagingItems: LazyPagingItems<Movie>,
    imageBaseUrlResult: Result<String>,
    onRetryClick: () -> Unit,
    onMovieClick: (Movie) -> Unit
) {

    when {
        imageBaseUrlResult is Result.Success && pagingItems.itemCount > 0  -> {
            Movies(
                pagingItems = pagingItems,
                imageBaseUrlResult.data,
                onMovieClick,
                onRetryClick
            )
        }

        imageBaseUrlResult is Result.Error || pagingItems.loadState.refresh is LoadState.Error -> {
            ErrorState(
                title = stringResource(
                    id = imageBaseUrlResult.getErrorTitleId()
                        ?: pagingItems.loadState.getErrorTitleId()
                        ?: R.string.something_went_wrong
                ),
                description = stringResource(
                    id = imageBaseUrlResult.getErrorDescriptionId()
                        ?: pagingItems.loadState.getErrorDescriptionId()
                        ?: R.string.something_went_wrong_description
                ),
                onRetryClick = onRetryClick
            )
        }

        else -> {
            LoadingState()
        }
    }
}

@Composable
private fun LoadingState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(78.dp),
            painter = painterResource(id = R.drawable.bazaar_logo),
            contentDescription = null,
            tint = Color.Unspecified
        )
        CircularProgressIndicator(
            modifier = Modifier
                .padding(top = 50.dp)
                .size(32.dp)
        )
    }
}

@Composable
private fun Movies(
    pagingItems: LazyPagingItems<Movie>,
    imageBaseUrl: String,
    onMovieClick: (Movie) -> Unit,
    onRetryClick: () -> Unit
) {
    val gridState = rememberLazyGridState()
    val orientation = LocalConfiguration.current.orientation
    val isLandscape by remember(orientation) {
        mutableStateOf(orientation == Configuration.ORIENTATION_LANDSCAPE)
    }
    MovieLazyVerticalGrid(
        modifier = Modifier,
        pagingItems = pagingItems,
        imageBaseUrl = imageBaseUrl,
        gridState = gridState,
        columnCount = if (isLandscape) 5 else 3,
        horizontalArrangement = Arrangement.spacedBy(if (isLandscape) 30.dp else 25.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        onMovieClick = onMovieClick,
        onRetryClick = onRetryClick
    )
}

@Composable
private fun MovieLazyVerticalGrid(
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

@Composable
private fun ErrorState(
    title: String,
    description: String,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val gradients = remember {
            listOf(GradientStart, GradientEnd)
        }
        Icon(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(Brush.verticalGradient(gradients))
                .padding(24.dp),
            painter = painterResource(id = R.drawable.ic_sad),
            tint = Color.Unspecified,
            contentDescription = null
        )
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
        Text(
            text = description,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )
        MovieButton(
            onClick = onRetryClick,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}