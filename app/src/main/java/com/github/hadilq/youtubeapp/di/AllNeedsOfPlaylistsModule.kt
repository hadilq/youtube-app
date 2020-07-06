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
package com.github.hadilq.youtubeapp.di

import android.content.Context
import android.content.SharedPreferences
import com.github.hadilq.youtubeapp.data.di.AbstractDataModule
import com.github.hadilq.youtubeapp.data.di.DataModule
import com.github.hadilq.youtubeapp.domain.di.AbstractDomainModule
import com.github.hadilq.youtubeapp.domain.di.DomainModule
import com.github.hadilq.youtubeapp.domain.navigation.Navigator
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
import com.github.hadilq.youtubeapp.playlists.di.AbstractPlaylistsModule
import com.github.hadilq.youtubeapp.playlists.di.PlaylistsModule

class AllNeedsOfPlaylistsModule(
  private val appModule: AppModule,
  private val domainModule: AbstractDomainModule,
  private val dataModule: AbstractDataModule,
  private val playlistsModule: AbstractPlaylistsModule
) : DomainModule by domainModule,
  AppModule by appModule,
  DataModule by dataModule,
  PlaylistsModule by playlistsModule {

  override val applicationContext: Context
    get() = appModule.applicationContext

  override val sharedPreferences: SharedPreferences
    get() = dataModule.sharedPreferences

  override val navigator: Navigator
    get() = domainModule.navigator
  override val youtubeRepository: YoutubeRepository
    get() = domainModule.youtubeRepository
  override val googlePlayRepository: GooglePlayRepository
    get() = domainModule.googlePlayRepository
  override val deviceRepository: DeviceRepository
    get() = domainModule.deviceRepository
  override val getPlaylists: GetPlaylists
    get() = domainModule.getPlaylists
  override val getPlaylistItems: GetPlaylistItems
    get() = domainModule.getPlaylistItems
  override val getSelectedAccountName: GetSelectedAccountName
    get() = domainModule.getSelectedAccountName
  override val isDeviceOnline: IsDeviceOnline
    get() = domainModule.isDeviceOnline
  override val isGooglePlayServicesAvailable: IsGooglePlayServicesAvailable
    get() = domainModule.isGooglePlayServicesAvailable
  override val isGoogleUserResolvableError: IsGoogleUserResolvableError
    get() = domainModule.isGoogleUserResolvableError
  override val newChooseAccountIntent: NewChooseAccountIntent
    get() = domainModule.newChooseAccountIntent
  override val setSelectedAccountName: SetSelectedAccountName
    get() = domainModule.setSelectedAccountName
}
