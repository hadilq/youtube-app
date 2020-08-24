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
package com.github.hadilq.youtubeapp.domain.di

import com.github.hadilq.youtubeapp.domain.usecase.*

abstract class AbstractDomainModule : DomainModule {

  override val getPlaylists: GetPlaylists
    get() = GetPlaylists()

  override val getPlaylistItems: GetPlaylistItems
    get() = GetPlaylistItems()

  override val getSelectedAccountName: GetSelectedAccountName
    get() = GetSelectedAccountName()

  override val handleErrors: HandleErrors
    get() = HandleErrors()

  override val isDeviceOnline: IsDeviceOnline
    get() = IsDeviceOnline()

  override val isGooglePlayServicesAvailable: IsGooglePlayServicesAvailable
    get() = IsGooglePlayServicesAvailable()

  override val isGoogleUserResolvableError: IsGoogleUserResolvableError
    get() = IsGoogleUserResolvableError()

  override val loadChannels: LoadChannels
    get() = LoadChannels()

  override val newChooseAccountIntent: NewChooseAccountIntent
    get() = NewChooseAccountIntent()

  override val setSelectedAccountName: SetSelectedAccountName
    get() = SetSelectedAccountName()
}
