package com.android.movie.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.movie.common.result.Result
import com.android.movie.data.repositories.configuration.ConfigurationRepository
import com.android.movie.data.repositories.movie.MovieRepository
import com.android.movie.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class HomeViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val dispatcherRule = MainDispatcherRule()

    @Rule
    @JvmField
    val mockRule = MockKRule(this)

    @RelaxedMockK
    lateinit var configurationRepository: ConfigurationRepository

    @RelaxedMockK
    lateinit var movieRepository: MovieRepository

    @Test
    fun imageBaseUrl_IsCached_SuccessWithImageBaseUrl() = runTest {
        val imageBaseUrl = "imageBaseUrl"
        coEvery { configurationRepository.imageBaseUrl } returns flowOf(imageBaseUrl)
        val viewModel = HomeViewModel(configurationRepository, movieRepository)
        val job = launch { viewModel.imageBaseUrl.collect() }
        assertEquals(Result.Success(imageBaseUrl), viewModel.imageBaseUrl.value)
        job.cancel()
    }

    @Test
    fun imageBaseUrl_IsNotCached_CallFetchImageBaseUrl() = runTest {
        coEvery { configurationRepository.imageBaseUrl } returns flowOf(null)
        HomeViewModel(configurationRepository, movieRepository)
        coVerify(exactly = 1) { configurationRepository.fetchImageBaseUrl() }
    }

    @Test
    fun imageBaseUrl_IsNotCachedAndFetchThrowsException_ErrorResult() = runTest {
        val throwable = Throwable("error message")
        coEvery { configurationRepository.imageBaseUrl } returns flowOf(null)
        coEvery { configurationRepository.fetchImageBaseUrl() } throws throwable
        val viewModel = HomeViewModel(configurationRepository, movieRepository)
        val job = launch { viewModel.imageBaseUrl.collect() }
        assertEquals(Result.Error(throwable), viewModel.imageBaseUrl.value)
        job.cancel()
    }
}