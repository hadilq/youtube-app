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
import com.github.hadilq.youtubeapp.core.util.execute
import com.github.hadilq.youtubeapp.domain.entity.AccountName
import com.github.hadilq.youtubeapp.domain.entity.Intent
import com.github.hadilq.youtubeapp.login.di.LoginModule
import com.google.android.gms.common.ConnectionResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.flow.receiveAsFlow

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class LoginViewModel : SLife() {

  private val playlistPublisher = Channel<Unit>(CONFLATED)
  private val showGooglePlayServicesAvailabilityErrorDialogPublisher = Channel<Int>(CONFLATED)
  private val chooseAccountPublisher = Channel<Pair<Intent, AccountName?>>(CONFLATED)
  private val noNetworkPublisher = Channel<Unit>(CONFLATED)

  val navToPlaylist = playlistPublisher.receiveAsFlow()
  val showGooglePlayServicesAvailabilityErrorDialog =
    showGooglePlayServicesAvailabilityErrorDialogPublisher.receiveAsFlow()
  val chooseAccount = chooseAccountPublisher.receiveAsFlow()
  val noNetwork = noNetworkPublisher.receiveAsFlow()

  fun LoginModule.loginPlease() = execute {
    if (!isGooglePlayServicesAvailable()) {
      acquireGooglePlayServices()
    } else if (getSelectedAccountName.run { execute() } == null) {
      chooseAccount()
    } else if (!isDeviceOnline.run { execute() }) {
      noNetworkPublisher.send(Unit)
    } else {
      playlistPublisher.send(Unit)
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
  }.sync()
}

class LoginViewModelFactory : LifeFactory<LoginViewModel> {
  override fun get(): LoginViewModel = LoginViewModel()
}
