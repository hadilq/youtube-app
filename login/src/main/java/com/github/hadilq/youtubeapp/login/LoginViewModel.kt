/**
 * Copyright 2020 Hadi Lashkari Ghouchani

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.hadilq.youtubeapp.login

import com.github.hadilq.androidlifecyclehandler.LifeFactory
import com.github.hadilq.androidlifecyclehandler.SLife
import com.github.hadilq.coroutinelifecyclehandler.observe
import com.github.hadilq.coroutinelifecyclehandler.observeIn
import com.github.hadilq.coroutinelifecyclehandler.toLife
import com.github.hadilq.youtubeapp.core.util.execute
import com.github.hadilq.youtubeapp.domain.entity.AccountName
import com.github.hadilq.youtubeapp.domain.entity.GooglePlayServicesAvailabilityError
import com.github.hadilq.youtubeapp.domain.entity.Intent
import com.github.hadilq.youtubeapp.domain.entity.Left
import com.github.hadilq.youtubeapp.domain.entity.Right
import com.github.hadilq.youtubeapp.domain.entity.UserRecoverableAuthIOError
import com.github.hadilq.youtubeapp.login.di.LoginModule
import com.google.android.gms.common.ConnectionResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.subscribeOn

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class LoginViewModel : SLife() {

  private val playlistPublisher = Channel<Unit>(CONFLATED)
  private val showGooglePlayServicesAvailabilityErrorDialogPublisher = Channel<Int>(CONFLATED)
  private val chooseAccountPublisher = Channel<Pair<Intent, AccountName?>>(CONFLATED)
  private val noNetworkPublisher = Channel<Unit>(CONFLATED)
  private val generalErrorPublisher = Channel<Unit>(CONFLATED)
  private val launchIntentPublisher = Channel<Intent>(CONFLATED)
  private val loadingPublisher = Channel<Boolean>(CONFLATED)

  val navToPlaylist = playlistPublisher.receiveAsFlow()
  val showGooglePlayServicesAvailabilityErrorDialog =
    showGooglePlayServicesAvailabilityErrorDialogPublisher.receiveAsFlow()
  val chooseAccount = chooseAccountPublisher.receiveAsFlow()
  val noNetwork = noNetworkPublisher.receiveAsFlow()
  val generalError = generalErrorPublisher.receiveAsFlow()
  val launchIntent = launchIntentPublisher.receiveAsFlow()
  val loading = loadingPublisher.receiveAsFlow()

  fun LoginModule.loginPlease() = execute {
    loadingPublisher.send(true)
    if (!isGooglePlayServicesAvailable()) {
      loadingPublisher.send(false)
      acquireGooglePlayServices()
    } else if (getSelectedAccountName.run { execute() } == null) {
      loadingPublisher.send(false)
      chooseAccount()
    } else if (!isDeviceOnline.run { execute() }) {
      loadingPublisher.send(false)
      noNetworkPublisher.send(Unit)
    } else {
      loadChannels.run { execute() }.onEach {
        when (it) {
          is Left -> {
            if (it.left.isEmpty()) {
              loadingPublisher.send(false)
              generalErrorPublisher.send(Unit)
            } else {
              loadingPublisher.send(false)
              playlistPublisher.send(Unit)
            }
          }
          is Right -> when (it.right) {
            is UserRecoverableAuthIOError -> launchIntentPublisher.send(it.right)
            is GooglePlayServicesAvailabilityError -> launchIntentPublisher.send(it.right)
            else -> {
              loadingPublisher.send(false)
              generalErrorPublisher.send(Unit)
            }
          }
        }
      }.toLife().sync()
    }
  }.sync()

  private suspend fun LoginModule.chooseAccount() {
    chooseAccountPublisher.send(
      newChooseAccountIntent.run { execute() } to getSelectedAccountName.run { execute() }
    )
  }

  private suspend fun LoginModule.isGooglePlayServicesAvailable(): Boolean {
    return isGooglePlayServicesAvailable.run { execute() } == ConnectionResult.SUCCESS
  }

  private suspend fun LoginModule.acquireGooglePlayServices() {
    val connectionStatusCode = isGooglePlayServicesAvailable.run { execute() }
    if (isGoogleUserResolvableError.run { execute(connectionStatusCode) }) {
      showGooglePlayServicesAvailabilityErrorDialogPublisher.send(connectionStatusCode)
    }
  }

  fun LoginModule.setSelectedAccountName(accountName: AccountName) = execute {
    setSelectedAccountName.run { execute(accountName) }
    loginPlease()
  }.sync()
}

class LoginViewModelFactory : LifeFactory<LoginViewModel> {
  override fun get(): LoginViewModel = LoginViewModel()
}
