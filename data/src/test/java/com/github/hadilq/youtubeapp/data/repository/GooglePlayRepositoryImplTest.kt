package com.github.hadilq.youtubeapp.data.repository

import com.github.hadilq.youtubeapp.data.di.FakeDomainModule
import com.github.hadilq.youtubeapp.domain.entity.ConnectionResult
import io.mockk.coEvery
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GooglePlayRepositoryImplTest {

  @Test
  fun isGooglePlayServicesAvailable() = with(FakeDomainModule()) {
    val anyConnectionResult: ConnectionResult = ConnectionResult.Fail(0)
    runBlocking {
      with(googleDataSource) { coEvery { isGooglePlayServicesAvailable() } returns anyConnectionResult }
      val repository = GooglePlayRepositoryImpl()

      val result = repository.run { isGooglePlayServicesAvailable() }

      Assert.assertEquals(result, anyConnectionResult)
    }
  }

  @Test
  fun isUserResolvableError() = with(FakeDomainModule()) {
    val anyConnectionResult: ConnectionResult = ConnectionResult.Fail(0)
    val anyResult = true
    runBlocking {
      with(googleDataSource) { coEvery { isUserResolvableError(anyConnectionResult) } returns anyResult }
      val repository = GooglePlayRepositoryImpl()

      val result = repository.run { isUserResolvableError(anyConnectionResult) }

      assertEquals(result, anyResult)
    }
  }
}
