package com.github.hadilq.youtubeapp.playlists

import com.github.hadilq.youtubeapp.domain.entity.GoogleAuthIOError
import com.github.hadilq.youtubeapp.domain.entity.GooglePlayServicesAvailabilityError
import com.github.hadilq.youtubeapp.domain.entity.Intent
import com.github.hadilq.youtubeapp.domain.entity.UserRecoverableAuthIOError
import com.github.hadilq.youtubeapp.playlists.di.FakePlaylistsModule
import com.github.hadilq.youtubeapp.playlists.util.test
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class PlaylistsViewModelTest {

  @Test
  fun `startWatchingForErrors must do nothing when no error emitted`() = runBlockingTest {
    with(FakePlaylistsModule(this)) {
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
      with(FakePlaylistsModule(this)) {
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
      with(FakePlaylistsModule(this)) {
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
      with(FakePlaylistsModule(this)) {
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
  fun startLoading() {
  }
}
