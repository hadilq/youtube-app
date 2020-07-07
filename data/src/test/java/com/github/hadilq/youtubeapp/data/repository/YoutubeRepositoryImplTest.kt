package com.github.hadilq.youtubeapp.data.repository

import com.github.hadilq.youtubeapp.data.di.FakeDomainModule
import com.github.hadilq.youtubeapp.domain.entity.AccountName
import com.github.hadilq.youtubeapp.domain.entity.Playlist
import com.github.hadilq.youtubeapp.domain.entity.Thumbnail
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import java.util.Date

@OptIn(ExperimentalUnsignedTypes::class)
@Suppress("MainFunctionReturnUnit")
class YoutubeRepositoryImplTest {

  @Test
  fun `getSelectedAccountName must return AccountName of DataSource`() = with(FakeDomainModule()) {
    val anyAccountName = "AnyAccountName"
    runBlocking {
      with(youtubeDataSource) { coEvery { getSelectedAccountName() } returns anyAccountName }
      val repository = YoutubeRepositoryImpl()

      val result = repository.run { getSelectedAccountName() }

      assertEquals(result, anyAccountName)
    }
  }

  @Test
  fun `getSelectedAccountName must return null if DataSource gives null`() = with(FakeDomainModule()) {
    runBlocking {
      with(youtubeDataSource) { coEvery { getSelectedAccountName() } returns null }
      with(preferencesDataSource) { coEvery { readString(any(), any()) } returns null }

      val repository = YoutubeRepositoryImpl()

      val result = repository.run { getSelectedAccountName() }

      assertEquals(result, null)
    }
  }

  @Test
  fun `getSelectedAccountName must setSelectedAccountName if preferences is not null`() = with(FakeDomainModule()) {
    val anyAccountName = "AnyAccountName"
    runBlocking {
      with(youtubeDataSource) { coEvery { getSelectedAccountName() } returns null }
      with(preferencesDataSource) { coEvery { readString(any(), any()) } returns anyAccountName }
      with(youtubeDataSource) { coJustRun { setSelectedAccountName(anyAccountName) } }

      val repository = YoutubeRepositoryImpl()

      val result = repository.run { getSelectedAccountName() }

      with(youtubeDataSource) { coVerify { getSelectedAccountName() } }
      with(preferencesDataSource) { coVerify { readString(any(), any()) } }
      with(youtubeDataSource) { coVerify { setSelectedAccountName(anyAccountName) } }
      assertEquals(result, anyAccountName)
    }
  }

  @Test
  fun `setSelectedAccountName must set AccountName to DataSource`() = with(FakeDomainModule()) {
    val anyAccountName = "AnyAccountName"
    runBlocking {
      with(youtubeDataSource) { coJustRun { setSelectedAccountName(any()) } }
      with(preferencesDataSource) { coJustRun { writeString(any(), any()) } }
      val repository = YoutubeRepositoryImpl()

      repository.run { setSelectedAccountName(anyAccountName) }

      with(youtubeDataSource) { coVerify { setSelectedAccountName(anyAccountName) } }
    }
  }

  @Test
  fun `newChooseAccountIntent must return the Intent`() = with(FakeDomainModule()) {
    val anyIntent = byteArrayOf()
    runBlocking {
      with(youtubeDataSource) { coEvery { newChooseAccountIntent() } returns anyIntent }
      val repository = YoutubeRepositoryImpl()

      val result = repository.run { newChooseAccountIntent() }

      assertEquals(result, anyIntent)
    }
  }

  @Test
  fun startLoadingPlaylist() = with(FakeDomainModule()) {
    val anyAccountName: AccountName = "AnyAccountName"
    val repository = YoutubeRepositoryImpl()
    runBlocking {
      with(youtubeDataSource) { coEvery { getSelectedAccountName() } returns anyAccountName }
      repository.run { startLoadingPlaylist() }
    }
    Unit
  }

  @Test
  fun startLoadingPlaylistItem() = with(FakeDomainModule()) {
    val anyAccountName: AccountName = "AnyAccountName"
    val playlist = Playlist("", Date(), "", Thumbnail("", 0u, 0u), "", 0u)
    val repository = YoutubeRepositoryImpl()
    runBlocking {
      with(youtubeDataSource) { coEvery { getSelectedAccountName() } returns anyAccountName }
      repository.run { startLoadingPlaylistItem(playlist) }
    }
    Unit
  }
}
