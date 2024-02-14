package com.android.movie.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.movie.common.result.Result
import com.android.movie.data.repositories.configuration.ConfigurationRepository
import com.android.movie.data.repositories.movie.MovieRepository
import com.android.movie.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val configurationRepository: ConfigurationRepository,
    movieRepository: MovieRepository
) : ViewModel() {

    private val _imageBaseUrl = MutableStateFlow<Result<String>>(Result.Loading)
    val imageBaseUrl: StateFlow<Result<String>>
        get() = _imageBaseUrl

    private var configurationJob: Job? = null

    val pager: Flow<PagingData<Movie>> =
        movieRepository.getMoviePagingData().cachedIn(viewModelScope)

    init {
        configurationRepository.imageBaseUrl.onEach {
            if (it == null) {
                getImageUrl()
            } else {
                _imageBaseUrl.value = Result.Success(it)
            }
        }.launchIn(viewModelScope)
    }

    fun getImageUrl() {
        configurationJob?.cancel()
        configurationJob = viewModelScope.launch {
            _imageBaseUrl.value = Result.Loading
            runCatching {
                configurationRepository.fetchImageBaseUrl()
            }.onFailure { throwable ->
                _imageBaseUrl.value = Result.Error(throwable)
            }
        }
    }
}