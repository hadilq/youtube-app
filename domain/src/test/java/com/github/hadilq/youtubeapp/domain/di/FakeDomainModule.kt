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

import com.github.hadilq.youtubeapp.domain.repository.DeviceRepository
import com.github.hadilq.youtubeapp.domain.repository.GooglePlayRepository
import com.github.hadilq.youtubeapp.domain.repository.YoutubeRepository
import com.github.hadilq.youtubeapp.domain.usecase.GetPlaylistItems
import com.github.hadilq.youtubeapp.domain.usecase.GetPlaylists
import com.github.hadilq.youtubeapp.domain.usecase.GetSelectedAccountName
import com.github.hadilq.youtubeapp.domain.usecase.IsDeviceOnline
import com.github.hadilq.youtubeapp.domain.usecase.IsGooglePlayServicesAvailable
import com.github.hadilq.youtubeapp.domain.usecase.IsGoogleUserResolvableError
import com.github.hadilq.youtubeapp.domain.usecase.NewChooseAccountIntent
import com.github.hadilq.youtubeapp.domain.usecase.SetSelectedAccountName
import io.mockk.mockk

class FakeDomainModule : DomainModule {

  override val youtubeRepository: YoutubeRepository = mockk()

  override val googlePlayRepository: GooglePlayRepository = mockk()

  override val deviceRepository: DeviceRepository = mockk()

  override val getPlaylists: GetPlaylists = mockk()

  override val getPlaylistItems: GetPlaylistItems = mockk()

  override val getSelectedAccountName: GetSelectedAccountName = mockk()

  override val isDeviceOnline: IsDeviceOnline = mockk()

  override val isGooglePlayServicesAvailable: IsGooglePlayServicesAvailable = mockk()

  override val isGoogleUserResolvableError: IsGoogleUserResolvableError = mockk()

  override val newChooseAccountIntent: NewChooseAccountIntent = mockk()

  override val setSelectedAccountName: SetSelectedAccountName = mockk()
}
