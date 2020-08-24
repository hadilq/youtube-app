package com.github.hadilq.youtubeapp.presentation.login

import com.github.hadilq.youtubeapp.domain.entity.*
import com.github.hadilq.youtubeapp.presentation.di.FakeModule
import com.github.hadilq.youtubeapp.presentation.util.test
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

@OptIn(InternalCoroutinesApi::class, ExperimentalCoroutinesApi::class)
internal class LoginViewModelTest {

  @Test
  fun `loginPlease would chooseAccount when ConnectionResult is SUCCESS`() = runBlockingTest {
    val anyConnectionResult: ConnectionResult = ConnectionResult.Success(0)
    with(FakeModule(this)) {
      with(LoginViewModel()) {
        onBorn()
        val loadingTest = loading.test(this@runBlockingTest)
        val chooseAccountTest = chooseAccount.test(this@runBlockingTest)
        val intent = Intent()
        with(isGooglePlayServicesAvailable) { coEvery { execute() } returns anyConnectionResult }
        with(getSelectedAccountName) { coEvery { execute() } returns null }
        with(newChooseAccountIntent) { coEvery { execute() } returns intent }

        loginPlease()

        with(isGooglePlayServicesAvailable) { coVerify { execute() } }
        with(getSelectedAccountName) { coVerify { execute() } }
        with(newChooseAccountIntent) { coVerify { execute() } }
        loadingTest.assertValues(true, false)
        chooseAccountTest.assertValues(Pair(intent, null))
        onDie()
        loadingTest.finish()
        chooseAccountTest.finish()
      }
    }
  }

  @Test
  fun `loginPlease would show just loading when PlayService is not available`() = runBlockingTest {
    val anyConnectionResult: ConnectionResult = ConnectionResult.Fail(0)
    with(FakeModule(this)) {
      with(LoginViewModel()) {
        onBorn()
        val loadingTest = loading.test(this@runBlockingTest)
        with(isGooglePlayServicesAvailable) { coEvery { execute() } returns anyConnectionResult }
        with(isGoogleUserResolvableError) { coEvery { execute(anyConnectionResult) } returns false }

        loginPlease()

        with(isGooglePlayServicesAvailable) { coVerify { execute() } }
        with(isGoogleUserResolvableError) { coVerify { execute(anyConnectionResult) } }
        loadingTest.assertValues(true, false)
        onDie()
        loadingTest.finish()
      }
    }
  }

  @Test
  fun `loginPlease would show error dialog when PlayService is not available and user has error`() =
    runBlockingTest {
      val anyResultCode = -50
      val anyConnectionResult: ConnectionResult = ConnectionResult.Fail(anyResultCode)
      with(FakeModule(this)) {
        with(LoginViewModel()) {
          onBorn()
          val loadingTest = loading.test(this@runBlockingTest)
          val showErrorDialogTest =
            showGooglePlayServicesAvailabilityErrorDialog.test(this@runBlockingTest)
          with(isGooglePlayServicesAvailable) { coEvery { execute() } returns anyConnectionResult }
          with(isGoogleUserResolvableError) { coEvery { execute(anyConnectionResult) } returns true }

          loginPlease()

          with(isGooglePlayServicesAvailable) { coVerify { execute() } }
          with(isGoogleUserResolvableError) { coVerify { execute(anyConnectionResult) } }
          showErrorDialogTest.assertValues(anyResultCode)
          loadingTest.assertValues(true, false)
          onDie()
          loadingTest.finish()
          showErrorDialogTest.finish()
        }
      }
    }

  @Test
  fun `loginPlease would show no network error when device is not online`() = runBlockingTest {
    val anyConnectionResult: ConnectionResult = ConnectionResult.Success(0)
    val anyAccountName = "AnyAccountName"
    with(FakeModule(this)) {
      with(LoginViewModel()) {
        onBorn()
        val loadingTest = loading.test(this@runBlockingTest)
        val noNetworkTest = noNetwork.test(this@runBlockingTest)
        with(isGooglePlayServicesAvailable) { coEvery { execute() } returns anyConnectionResult }
        with(getSelectedAccountName) { coEvery { execute() } returns anyAccountName }
        with(isDeviceOnline) { coEvery { execute() } returns false }

        loginPlease()

        with(isGooglePlayServicesAvailable) { coVerify { execute() } }
        with(getSelectedAccountName) { coVerify { execute() } }
        with(isDeviceOnline) { coVerify { execute() } }
        loadingTest.assertValues(true, false)
        noNetworkTest.assertValues(Unit)
        onDie()
        loadingTest.finish()
        noNetworkTest.finish()
      }
    }
  }

  @Test
  fun `loginPlease would show general error when channel list is empty`() = runBlockingTest {
    val anyConnectionResult: ConnectionResult = ConnectionResult.Success(0)
    val anyAccountName = "AnyAccountName"
    with(FakeModule(this)) {
      with(LoginViewModel()) {
        onBorn()
        val loadingTest = loading.test(this@runBlockingTest)
        val generalErrorTest = generalError.test(this@runBlockingTest)
        with(isGooglePlayServicesAvailable) { coEvery { execute() } returns anyConnectionResult }
        with(getSelectedAccountName) { coEvery { execute() } returns anyAccountName }
        with(isDeviceOnline) { coEvery { execute() } returns true }
        with(loadChannels) { coEvery { execute() } returns flowOf(Left(emptyList())) }

        loginPlease()

        with(isGooglePlayServicesAvailable) { coVerify { execute() } }
        with(getSelectedAccountName) { coVerify { execute() } }
        with(isDeviceOnline) { coVerify { execute() } }
        with(loadChannels) { coVerify { execute() } }
        loadingTest.assertValues(true, false)
        generalErrorTest.assertValues(Unit)
        onDie()
        loadingTest.finish()
        generalErrorTest.finish()
      }
    }
  }

  @Test
  fun `loginPlease would launch playlist when channel list is not empty`() = runBlockingTest {
    val anyConnectionResult: ConnectionResult = ConnectionResult.Success(0)
    val anyAccountName = "AnyAccountName"
    with(FakeModule(this)) {
      with(LoginViewModel()) {
        onBorn()
        val loadingTest = loading.test(this@runBlockingTest)
        val navToPlaylistTest = navToPlaylist.test(this@runBlockingTest)
        with(isGooglePlayServicesAvailable) { coEvery { execute() } returns anyConnectionResult }
        with(getSelectedAccountName) { coEvery { execute() } returns anyAccountName }
        with(isDeviceOnline) { coEvery { execute() } returns true }
        with(loadChannels) { coEvery { execute() } returns flowOf(Left(listOf(Channel("", "")))) }

        loginPlease()

        with(isGooglePlayServicesAvailable) { coVerify { execute() } }
        with(getSelectedAccountName) { coVerify { execute() } }
        with(isDeviceOnline) { coVerify { execute() } }
        with(loadChannels) { coVerify { execute() } }
        loadingTest.assertValues(true, false)
        navToPlaylistTest.assertValues(Unit)
        onDie()
        loadingTest.finish()
        navToPlaylistTest.finish()
      }
    }
  }

  @Test
  fun `loginPlease would launch intent when UserRecoverableAuthIOError happened`() =
    runBlockingTest {
      val anyConnectionResult: ConnectionResult = ConnectionResult.Success(0)
      val anyAccountName = "AnyAccountName"
      val intent = Intent()
      with(FakeModule(this)) {
        with(LoginViewModel()) {
          onBorn()
          val loadingTest = loading.test(this@runBlockingTest)
          val launchIntentTest = launchIntent.test(this@runBlockingTest)
          with(isGooglePlayServicesAvailable) { coEvery { execute() } returns anyConnectionResult }
          with(getSelectedAccountName) { coEvery { execute() } returns anyAccountName }
          with(isDeviceOnline) { coEvery { execute() } returns true }
          with(loadChannels) {
            coEvery { execute() } returns
                flowOf(Right(UserRecoverableAuthIOError(RuntimeException(), intent)))
          }

          loginPlease()

          with(isGooglePlayServicesAvailable) { coVerify { execute() } }
          with(getSelectedAccountName) { coVerify { execute() } }
          with(isDeviceOnline) { coVerify { execute() } }
          with(loadChannels) { coVerify { execute() } }
          loadingTest.assertValues(true)
          launchIntentTest.assertValues(intent)
          onDie()
          loadingTest.finish()
          launchIntentTest.finish()
        }
      }
    }

  @Test
  fun `loginPlease would launch intent when GooglePlayServicesAvailabilityError happened`() =
    runBlockingTest {
      val anyConnectionResult: ConnectionResult = ConnectionResult.Success(0)
      val anyAccountName = "AnyAccountName"
      val intent = Intent()
      with(FakeModule(this)) {
        with(LoginViewModel()) {
          onBorn()
          val loadingTest = loading.test(this@runBlockingTest)
          val launchIntentTest = launchIntent.test(this@runBlockingTest)
          with(isGooglePlayServicesAvailable) { coEvery { execute() } returns anyConnectionResult }
          with(getSelectedAccountName) { coEvery { execute() } returns anyAccountName }
          with(isDeviceOnline) { coEvery { execute() } returns true }
          with(loadChannels) {
            coEvery { execute() } returns
                flowOf(
                  Right<List<Channel>, Error>(
                    GooglePlayServicesAvailabilityError(
                      RuntimeException(),
                      intent
                    )
                  )
                )
          }

          loginPlease()

          with(isGooglePlayServicesAvailable) { coVerify { execute() } }
          with(getSelectedAccountName) { coVerify { execute() } }
          with(isDeviceOnline) { coVerify { execute() } }
          with(loadChannels) { coVerify { execute() } }
          loadingTest.assertValues(true)
          launchIntentTest.assertValues(intent)
          onDie()
          loadingTest.finish()
          launchIntentTest.finish()
        }
      }
    }

  @Test
  fun `loginPlease would launch intent when GoogleAuthIOError happened`() = runBlockingTest {
    val anyConnectionResult: ConnectionResult = ConnectionResult.Success(0)
    val anyAccountName = "AnyAccountName"
    with(FakeModule(this)) {
      with(LoginViewModel()) {
        onBorn()
        val loadingTest = loading.test(this@runBlockingTest)
        val generalErrorTest = generalError.test(this@runBlockingTest)
        with(isGooglePlayServicesAvailable) { coEvery { execute() } returns anyConnectionResult }
        with(getSelectedAccountName) { coEvery { execute() } returns anyAccountName }
        with(isDeviceOnline) { coEvery { execute() } returns true }
        with(loadChannels) {
          coEvery { execute() } returns
              flowOf(Right<List<Channel>, Error>(GoogleAuthIOError(RuntimeException())))
        }

        loginPlease()

        with(isGooglePlayServicesAvailable) { coVerify { execute() } }
        with(getSelectedAccountName) { coVerify { execute() } }
        with(isDeviceOnline) { coVerify { execute() } }
        with(loadChannels) { coVerify { execute() } }
        loadingTest.assertValues(true, false)
        generalErrorTest.assertValues(Unit)
        onDie()
        loadingTest.finish()
        generalErrorTest.finish()
      }
    }
  }

  @Test
  fun `setSelectedAccountName would show just loading when PlayService is not available`() =
    runBlockingTest {
      val anyAccountName: AccountName = "AnyAccountName"
      val anyConnectionResult: ConnectionResult = ConnectionResult.Fail(0)
      with(FakeModule(this)) {
        with(LoginViewModel()) {
          onBorn()
          val loadingTest = loading.test(this@runBlockingTest)
          with(isGooglePlayServicesAvailable) { coEvery { execute() } returns anyConnectionResult }
          with(isGoogleUserResolvableError) { coEvery { execute(anyConnectionResult) } returns false }
          with(setSelectedAccountName) { coJustRun { execute(anyAccountName) } }

          setSelectedAccountName(anyAccountName)

          with(isGooglePlayServicesAvailable) { coVerify { execute() } }
          with(isGoogleUserResolvableError) { coVerify { execute(anyConnectionResult) } }
          with(setSelectedAccountName) { coVerify { execute(anyAccountName) } }
          loadingTest.assertValues(true, false)
          with(setSelectedAccountName) { coVerify { execute(anyAccountName) } }
          onDie()
          loadingTest.finish()
        }
      }
    }
}
