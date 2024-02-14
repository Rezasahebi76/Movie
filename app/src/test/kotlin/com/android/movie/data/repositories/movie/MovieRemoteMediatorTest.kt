package com.android.movie.data.repositories.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.android.movie.data.datasource.local.movie.MoviesLocalDataSource
import com.android.movie.data.datasource.remote.MoviesRemoteDataSource
import com.android.movie.database.entities.MovieEntity
import com.android.movie.network.model.MovieResponse
import com.android.movie.network.model.MoviesResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediatorTest {

    @Rule
    @JvmField
    val mockRule = MockKRule(this)

    @RelaxedMockK
    lateinit var moviesLocalDataSource: MoviesLocalDataSource

    @RelaxedMockK
    lateinit var moviesRemoteDataSource: MoviesRemoteDataSource


    private lateinit var remoteMediator: MovieRemoteMediator

    @BeforeTest
    fun setup() {
        remoteMediator = MovieRemoteMediator(
            moviesRemoteDataSource,
            moviesLocalDataSource,
        )
    }

    @Test
    fun load_StateIsPrepend_ReturnSuccessResultWithEndOfPagination() = runTest {
        val pageState = getPageState()
        val result = remoteMediator.load(LoadType.PREPEND, pageState)
        assertTrue { result is RemoteMediator.MediatorResult.Success }
        assertTrue { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
        coVerify(exactly = 0) {
            moviesRemoteDataSource.getUpcomingMovies(any())
            moviesLocalDataSource.refreshAllMovies(any())
            moviesLocalDataSource.insertMovies(any())
        }
    }

    @Test
    fun load_StateIsRefreshAndMoreDataIsExist_ReturnSuccessResultWithoutEndOfPagination() =
        runTest {
            val pageState = getPageState()
            coEvery { moviesRemoteDataSource.getUpcomingMovies(any()) } returns getMoviesResponse(
                page = 1,
                totalPage = 2
            )
            val result = remoteMediator.load(LoadType.REFRESH, pageState)
            assertTrue { result is RemoteMediator.MediatorResult.Success }
            assertFalse { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
        }

    @Test
    fun load_StateIsRefresh_CallRefreshAllMovies() = runTest {
        val pageState = getPageState()
        coEvery { moviesRemoteDataSource.getUpcomingMovies(any()) } returns getMoviesResponse()
        remoteMediator.load(LoadType.REFRESH, pageState)
        coVerify(exactly = 1) {
            moviesRemoteDataSource.getUpcomingMovies(1)
            moviesLocalDataSource.refreshAllMovies(any())
        }
        coVerify(exactly = 0) {
            moviesLocalDataSource.insertMovies(any())
        }
    }

    @Test
    fun load_StateIsAppend_ReturnSuccessResultWithEndOfPagination() = runTest {
        val pagingState = getPageState(
            listOf(
                Page(
                    data = getMovieEntities(2),
                    prevKey = null,
                    nextKey = null
                )
            )
        )
        coEvery { moviesRemoteDataSource.getUpcomingMovies(any()) } returns getMoviesResponse(
            page = 2,
            totalPage = 2
        )
        val result = remoteMediator.load(LoadType.APPEND, pagingState)
        coVerify(exactly = 1) {
            moviesRemoteDataSource.getUpcomingMovies(2)
        }
        assertTrue { result is RemoteMediator.MediatorResult.Success }
        assertTrue { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
    }

    @Test
    fun load_StateIsAppend_CallInsertMovies() = runTest {
        val pagingState = getPageState(
            listOf(
                Page(
                    data = getMovieEntities(2),
                    prevKey = null,
                    nextKey = null
                )
            )
        )
        coEvery { moviesRemoteDataSource.getUpcomingMovies(any()) } returns getMoviesResponse(
            page = 2,
            totalPage = 2
        )
        remoteMediator.load(LoadType.APPEND, pagingState)
        coVerify(exactly = 1) {
            moviesLocalDataSource.insertMovies(any())
        }
    }

    @Test
    fun load_StateIsAppendAndNextPageIsNull_ReturnSuccessResultWithEndOfPagination() = runTest {
        val pagingState = getPageState(
            listOf(
                Page(
                    data = getMovieEntities(null),
                    prevKey = null,
                    nextKey = null
                )
            )
        )
        val result = remoteMediator.load(LoadType.APPEND, pagingState)
        coVerify(exactly = 0) {
            moviesRemoteDataSource.getUpcomingMovies(any())
            moviesLocalDataSource.insertMovies(any())
        }
        assertTrue { result is RemoteMediator.MediatorResult.Success }
        assertTrue { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
    }

    @Test
    fun load_StateIsAppendAndLastItemIsNull_ReturnSuccessResult() = runTest {
        val pagingState = getPageState()
        coEvery { moviesRemoteDataSource.getUpcomingMovies(any()) } returns getMoviesResponse()
        val result = remoteMediator.load(LoadType.APPEND, pagingState)
        assertTrue { result is RemoteMediator.MediatorResult.Success }
        assertFalse { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
    }

    private fun getPageState(pages: List<Page<Int, MovieEntity>> = listOf()): PagingState<Int, MovieEntity> =
        PagingState(
            pages = pages,
            anchorPosition = null,
            config = PagingConfig(10),
            leadingPlaceholderCount = 0
        )

    private fun getMoviesResponse(page: Int = 1, totalPage: Int = page): MoviesResponse =
        MoviesResponse(
            page = page,
            results = listOf(
                MovieResponse(
                    id = 1,
                    title = "Title",
                    posterPath = "posterPath"
                )
            ),
            totalPages = totalPage
        )

    private fun getMovieEntities(nextPage: Int?): List<MovieEntity> = listOf(
        MovieEntity(
            id = 0,
            title = "Title",
            posterPath = "posterPath",
            remoteId = 1,
            nextPage = nextPage
        )
    )

}