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

import android.Manifest
import android.accounts.AccountManager
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.hadilq.androidlifecyclehandler.provideLife
import com.github.hadilq.coroutinelifecyclehandler.observe
import com.github.hadilq.youtubeapp.domain.di.App
import com.github.hadilq.youtubeapp.domain.entity.AccountName
import com.github.hadilq.youtubeapp.domain.navigation.Playlist
import com.github.hadilq.youtubeapp.login.di.LoginModule
import com.github.hadilq.youtubeapp.login.di.fix
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.android.synthetic.main.login.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

@OptIn(ExperimentalCoroutinesApi::class)
class LoginActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

  private val module: LoginModule = (application as App).appComponent.loginModule.fix()

  private lateinit var viewModel: LoginViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.login)
    viewModel = provideLife(module.loginViewModelFactory)

    with(viewModel) {
      navToPlaylist.observe()() { navigateToPlaylist() }
      showGooglePlayServicesAvailabilityErrorDialog.observe()() { showGooglePlayServicesAvailabilityErrorDialog(it) }
      chooseAccount.observe()() { chooseAccount() }
      noNetwork.observe()() { noNetworkError() }
    }

    setupListeners()
  }

  override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
  ) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      REQUEST_GOOGLE_PLAY_SERVICES -> if (resultCode != Activity.RESULT_OK) {
        noGooglePlayService()
      } else {
        getResultsFromApi()
      }
      REQUEST_ACCOUNT_PICKER -> if (resultCode == Activity.RESULT_OK && data != null && data.extras != null
      ) {
        val accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
        if (accountName != null) {
          setSelectedAccountName(accountName)
          getResultsFromApi()
        }
      }
      REQUEST_AUTHORIZATION -> if (resultCode == Activity.RESULT_OK) {
        getResultsFromApi()
      }
    }
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    EasyPermissions.onRequestPermissionsResult(
      requestCode, permissions, grantResults, this
    )
  }

  override fun onPermissionsGranted(requestCode: Int, list: List<String>) = Unit

  override fun onPermissionsDenied(requestCode: Int, list: List<String>) = Unit

  private fun noNetworkError() {
  }

  private fun noGooglePlayService() {
  }

  private fun navigateToPlaylist() {
    module.navigator.navigateTo(Playlist)
  }

  private fun showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode: Int) {
    val apiAvailability = GoogleApiAvailability.getInstance()
    val dialog: Dialog = apiAvailability.getErrorDialog(
      this,
      connectionStatusCode,
      REQUEST_GOOGLE_PLAY_SERVICES
    )
    dialog.show()
  }

  private fun setupListeners() {
    btnLogin.setOnClickListener {
      btnLogin.isEnabled = false
      getResultsFromApi()
      btnLogin.isEnabled = true
    }
  }

  @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
  private fun chooseAccount() {
    if (EasyPermissions.hasPermissions(this, Manifest.permission.GET_ACCOUNTS)) {
      val accountName = getSelectedAccountName()
      if (accountName != null) {
        getResultsFromApi()
      } else {
        // Start a dialog from which the user can choose an account
        startActivityForResult(
          newChooseAccountIntent(),
          REQUEST_ACCOUNT_PICKER
        )
      }
    } else {
      // Request the GET_ACCOUNTS permission via a user dialog
      EasyPermissions.requestPermissions(
        this,
        "This app needs to access your Google account (via Contacts).",
        REQUEST_PERMISSION_GET_ACCOUNTS,
        Manifest.permission.GET_ACCOUNTS
      )
    }
  }

  private fun getResultsFromApi() {
    module.run { viewModel.run { loginPlease() } }
  }

  private fun getSelectedAccountName(): AccountName? =
    module.run { viewModel.run { getSelectedAccountName() } }

  private fun setSelectedAccountName(accountName: AccountName) {
    module.run { viewModel.run { setSelectedAccountName(accountName) } }
  }

  private fun newChooseAccountIntent(): Intent? =
    module.run { viewModel.run { newChooseAccountIntent() } }?.let {
      module.parcelableUtil.unmarshall(it, Intent.CREATOR)
    }
}

private const val REQUEST_ACCOUNT_PICKER = 1000
private const val REQUEST_AUTHORIZATION = 1001
private const val REQUEST_GOOGLE_PLAY_SERVICES = 1002
private const val REQUEST_PERMISSION_GET_ACCOUNTS = 1003

