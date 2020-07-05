package com.github.hadilq.youtubeapp.data.repository

import com.github.hadilq.youtubeapp.data.di.FakeDomainModule
import com.github.hadilq.youtubeapp.domain.entity.Playlist
import com.github.hadilq.youtubeapp.domain.entity.Thumbnail
import io.mockk.every
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import java.util.Date

@Suppress("MainFunctionReturnUnit")
class YoutubeRepositoryImplTest {

  @Test
  fun `getSelectedAccountName must return AccountName of DataSource`() = with(FakeDomainModule()) {
    val anyAccountName = "AnyAccountName"
    with(youtubeDataSource) {
      every { getSelectedAccountName() } returns anyAccountName
      val repository = YoutubeRepositoryImpl()

      val result = repository.run { getSelectedAccountName() }

      assertEquals(result, anyAccountName)
    }
  }

  @Test
  fun `getSelectedAccountName must return null if DataSource gives null`() = with(FakeDomainModule()) {
    with(youtubeDataSource) {
      every { getSelectedAccountName() } returns null
      val repository = YoutubeRepositoryImpl()

      val result = repository.run { getSelectedAccountName() }

      assertEquals(result, null)
    }
  }

  @Test
  fun `setSelectedAccountName must set AccountName to DataSource`() = with(FakeDomainModule()) {
    val anyAccountName = "AnyAccountName"
    with(youtubeDataSource) {
      every { setSelectedAccountName(any()) } returns Unit
      val repository = YoutubeRepositoryImpl()

      repository.run { setSelectedAccountName(anyAccountName) }

      verify {
        setSelectedAccountName(anyAccountName)
      }
    }
  }

  @Test
  fun `newChooseAccountIntent must return the Intent`() = with(FakeDomainModule()) {
    val anyIntent = byteArrayOf()
    with(youtubeDataSource) {
      every { newChooseAccountIntent() } returns anyIntent
      val repository = YoutubeRepositoryImpl()

      val result = repository.run { newChooseAccountIntent() }

      assertEquals(result, anyIntent)
    }
  }

  @Test
  fun startLoadingPlaylist() = with(FakeDomainModule()) {
    val repository = YoutubeRepositoryImpl()
    repository.run { startLoadingPlaylist() }
  }

  @Suppress("EXPERIMENTAL_UNSIGNED_LITERALS")
  @Test
  fun startLoadingPlaylistItem() = with(FakeDomainModule()) {
    val playlist = Playlist("", Date(), "", Thumbnail("", 0u, 0u), "", 0u)
    val repository = YoutubeRepositoryImpl()
    repository.run { startLoadingPlaylistItem(playlist) }
  }
}
