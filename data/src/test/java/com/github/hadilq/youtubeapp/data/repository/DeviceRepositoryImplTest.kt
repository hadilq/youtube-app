package com.github.hadilq.youtubeapp.data.repository

import com.github.hadilq.youtubeapp.data.di.FakeDomainModule
import io.mockk.coEvery
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.jupiter.api.Test

internal class DeviceRepositoryImplTest {

  @Test
  fun isDeviceOnline() = with(FakeDomainModule()) {
    val anyResult = true
    runBlocking {
      with(deviceDataSource) { coEvery { isDeviceOnline() } returns anyResult }
      val repository = DeviceRepositoryImpl()

      val result = repository.run { isDeviceOnline() }

      Assert.assertEquals(result, anyResult)
    }
  }
}
