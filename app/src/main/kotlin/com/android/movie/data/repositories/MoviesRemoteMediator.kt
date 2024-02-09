package com.android.movie.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.android.movie.data.datasource.local.movie.MoviesLocalDataSource
import com.android.movie.data.datasource.local.preferences.PreferencesLocalDataSource
import com.android.movie.data.datasource.remote.MoviesRemoteDataSource
import com.android.movie.data.mapper.toMovieEntity
import com.android.movie.database.entities.MovieEntity
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator @Inject constructor(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource,
    private val preferencesLocalDataSource: PreferencesLocalDataSource
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val nextPage: Int = when (loadType) {
                LoadType.REFRESH -> {
                    1
                }

                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)

                LoadType.APPEND -> {
                    preferencesLocalDataSource.lastPage.first() + 1
                }
            }
            val response = remoteDataSource.getUpcomingMovies(nextPage)
            val movies = response.results.map { it.toMovieEntity() }
            if (loadType == LoadType.REFRESH) {
                localDataSource.refreshAllMovies(movies)
            } else {
                localDataSource.insertMovies(movies)
            }
            preferencesLocalDataSource.updateLastPage(nextPage)
            MediatorResult.Success(endOfPaginationReached = response.totalPages == nextPage)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }
}