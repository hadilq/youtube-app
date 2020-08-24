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
import coil.ImageLoader
import com.github.hadilq.youtubeapp.core.di.AbstractCoreModule
import com.github.hadilq.youtubeapp.core.di.CoreModule
import com.github.hadilq.youtubeapp.core.navigation.Navigator
import com.github.hadilq.youtubeapp.data.di.AbstractDataModule
import com.github.hadilq.youtubeapp.data.di.DataModule
import com.github.hadilq.youtubeapp.domain.di.AbstractDomainModule
import com.github.hadilq.youtubeapp.domain.di.DomainModule
import com.github.hadilq.youtubeapp.domain.repository.DeviceRepository
import com.github.hadilq.youtubeapp.domain.repository.GooglePlayRepository
import com.github.hadilq.youtubeapp.domain.repository.YoutubeRepository
import com.github.hadilq.youtubeapp.domain.usecase.*
import com.github.hadilq.youtubeapp.playlists.di.AbstractPlaylistsModule
import com.github.hadilq.youtubeapp.playlists.di.PlaylistsModule
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import kotlinx.coroutines.CoroutineScope

class AllNeedsOfPlaylistsModule(
  private val appModule: AppModule,
  private val domainModule: AbstractDomainModule,
  private val coreModule: AbstractCoreModule,
  private val dataModule: AbstractDataModule,
  private val playlistsModule: AbstractPlaylistsModule
) : DomainModule by domainModule,
  CoreModule by coreModule,
  AppModule by appModule,
  DataModule by dataModule,
  PlaylistsModule by playlistsModule {

  override val applicationContext: Context
    get() = appModule.applicationContext

  override val googleAccountCredential: GoogleAccountCredential
    get() = appModule.googleAccountCredential

  override val imageLoader: ImageLoader
    get() = appModule.imageLoader

  override val viewModelScope: CoroutineScope
    get() = coreModule.viewModelScope

  override val sharedPreferences: SharedPreferences
    get() = dataModule.sharedPreferences

  override fun navigator(context: Context): Navigator = coreModule.navigator(context)
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
  override val handleErrors: HandleErrors
    get() = domainModule.handleErrors
  override val isDeviceOnline: IsDeviceOnline
    get() = domainModule.isDeviceOnline
  override val isGooglePlayServicesAvailable: IsGooglePlayServicesAvailable
    get() = domainModule.isGooglePlayServicesAvailable
  override val isGoogleUserResolvableError: IsGoogleUserResolvableError
    get() = domainModule.isGoogleUserResolvableError
  override val loadChannels: LoadChannels
    get() = domainModule.loadChannels
  override val newChooseAccountIntent: NewChooseAccountIntent
    get() = domainModule.newChooseAccountIntent
  override val setSelectedAccountName: SetSelectedAccountName
    get() = domainModule.setSelectedAccountName
}
