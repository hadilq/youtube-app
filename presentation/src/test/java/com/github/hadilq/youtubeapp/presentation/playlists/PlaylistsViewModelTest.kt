package com.github.hadilq.youtubeapp.presentation.playlists

import androidx.paging.PagingData
import com.github.hadilq.youtubeapp.domain.entity.*
import com.github.hadilq.youtubeapp.presentation.di.FakeModule
import com.github.hadilq.youtubeapp.presentation.util.test
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class PlaylistsViewModelTest {

  @Test
  fun `startWatchingForErrors must do nothing when no error emitted`() = runBlockingTest {
    with(FakeModule(this)) {
      with(PlaylistsViewModel()) {
        onBorn()
        val navToLoginTest = navToLogin.test(this@runBlockingTest)
        with(handleErrors) { coEvery { execute() } returns flowOf() }

        startWatchingForErrors()

        navToLoginTest.assertNoValues()
        onDie()
        navToLoginTest.finish()
      }
    }
  }

  @Test
  fun `startWatchingForErrors must launch login with intent when UserRecoverableAuthIOError happened`() =
    runBlockingTest {
      val intent = Intent()
      with(FakeModule(this)) {
        with(PlaylistsViewModel()) {
          onBorn()
          val navToLoginTest = navToLogin.test(this@runBlockingTest)
          with(handleErrors) {
            coEvery { execute() } returns
                flowOf(UserRecoverableAuthIOError(RuntimeException(), intent))
          }

          startWatchingForErrors()

          with(handleErrors) { coVerify { execute() } }
          navToLoginTest.assertValues(intent)
          onDie()
          navToLoginTest.finish()
        }
      }
    }

  @Test
  fun `startWatchingForErrors must launch login with intent when GooglePlayServicesAvailabilityError happened`() =
    runBlockingTest {
      val intent = Intent()
      with(FakeModule(this)) {
        with(PlaylistsViewModel()) {
          onBorn()
          val navToLoginTest = navToLogin.test(this@runBlockingTest)
          with(handleErrors) {
            coEvery { execute() } returns
                flowOf(GooglePlayServicesAvailabilityError(RuntimeException(), intent))
          }

          startWatchingForErrors()

          with(handleErrors) { coVerify { execute() } }
          navToLoginTest.assertValues(intent)
          onDie()
          navToLoginTest.finish()
        }
      }
    }

  @Test
  fun `startWatchingForErrors must launch login without intent when GoogleAuthIOError happened`() =
    runBlockingTest {
      with(FakeModule(this)) {
        with(PlaylistsViewModel()) {
          onBorn()
          val navToLoginTest = navToLogin.test(this@runBlockingTest)
          with(handleErrors) {
            coEvery { execute() } returns
                flowOf(GoogleAuthIOError(RuntimeException()))
          }
          with(setSelectedAccountName) { coJustRun { execute(null) } }

          startWatchingForErrors()

          with(setSelectedAccountName) { coVerify { execute(null) } }
          navToLoginTest.assertValues(null)
          onDie()
          navToLoginTest.finish()
        }
      }
    }

  @Test
  fun startLoading() = runBlockingTest{
    with(FakeModule(this)) {
      with(PlaylistsViewModel()) {
        onBorn()
        val playlistsTest = playlists.test(this@runBlockingTest)
        val pagingData : PagingData<Playlist> = mockk()
        with(getPlaylists) { coEvery { execute(any()) } returns flowOf(pagingData) }

        startLoading()

        playlistsTest.assertValues(pagingData)
        playlistsTest.finish()
        onDie()
      }
    }
  }
}
