package com.android.movie.data.repositories.mapper

import com.android.movie.data.mapper.toMovie
import com.android.movie.data.mapper.toMovieEntities
import com.android.movie.database.entities.MovieEntity
import com.android.movie.models.Movie
import com.android.movie.network.model.movie.MovieResponse
import com.android.movie.network.model.movie.MoviesResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class MovieMapperTest {

    @Test
    fun moviesResponseToMoviesEntity_PageEqualsTotalPage_ReturnMovieEntitiesWithNextPageNull() {
        assertEquals(
            expected = listOf(
                MovieEntity(
                    id = 0,
                    title = "Title",
                    posterPath = "posterPath",
                    remoteId = 1,
                    nextPage = null
                )
            ),
            actual = MoviesResponse(
                page = 1,
                results = listOf(
                    MovieResponse(
                        id = 1,
                        title = "Title",
                        posterPath = "posterPath"
                    )
                ),
                totalPages = 1
            ).toMovieEntities()
        )
    }

    @Test
    fun moviesResponseToMoviesEntity_PageNotEqualsTotalPage_ReturnMovieEntitiesWithNotNullNextPage() {
        assertEquals(
            expected = listOf(
                MovieEntity(
                    id = 0,
                    title = "Title",
                    posterPath = "posterPath",
                    remoteId = 1,
                    nextPage = 2
                )
            ),
            actual = MoviesResponse(
                page = 1,
                results = listOf(
                    MovieResponse(
                        id = 1,
                        title = "Title",
                        posterPath = "posterPath"
                    )
                ),
                totalPages = 2
            ).toMovieEntities()
        )
    }

    @Test
    fun movieEntityToMovie() {
        assertEquals(
            expected = Movie(1, "Title", "posterPath"),
            actual = MovieEntity(1, "Title", "posterPath", 1, null).toMovie()
        )
    }
}
