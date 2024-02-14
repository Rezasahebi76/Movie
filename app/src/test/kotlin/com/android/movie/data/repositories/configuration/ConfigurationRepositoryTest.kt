//package com.android.movie.data.repositories.configuration
//
//import com.android.movie.data.datasource.local.preferences.PreferencesLocalDataSource
//import com.android.movie.data.datasource.remote.configuration.ConfigurationRemoteDataSource
//import com.android.movie.network.model.configuration.ImageConfigurationResponse
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.impl.annotations.RelaxedMockK
//import io.mockk.junit4.MockKRule
//import kotlinx.coroutines.test.runTest
//import org.junit.Rule
//import kotlin.test.BeforeTest
//import kotlin.test.Test
//
//class ConfigurationRepositoryTest {
//
//    @Rule
//    @JvmField
//    val mockRule = MockKRule(this)
//
//    @RelaxedMockK
//    lateinit var preferencesLocalDataSource: PreferencesLocalDataSource
//
//    @RelaxedMockK
//    lateinit var configurationRemoteDataSource: ConfigurationRemoteDataSource
//
//    private lateinit var repository :ConfigurationRepository
//
//    @BeforeTest
//    fun setup(){
//        repository = ConfigurationRepositoryImpl(preferencesLocalDataSource,configurationRemoteDataSource)
//    }
//
//    @Test
//    fun fetchImageBaseUrl_Success_UpdatedBaseUrlAndSizeInPreferences() = runTest{
//        coEvery { configurationRemoteDataSource.getImageConfiguration() } returns ImageConfigurationResponse("baseUrl" , listOf("/w500","/original"))
//        repository.fetchImageBaseUrl()
//        coVerify(exactly = 1) {
//            configurationRemoteDataSource.getImageConfiguration()
//            preferencesLocalDataSource.updateImageBaseUrl("baseUrl/original")
//        }
//    }
//
//}
