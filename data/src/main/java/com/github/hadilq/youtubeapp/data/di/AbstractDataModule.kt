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
package com.github.hadilq.youtubeapp.data.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.github.hadilq.youtubeapp.data.database.AppDatabase
import com.github.hadilq.youtubeapp.data.database.dao.PlaylistDao
import com.github.hadilq.youtubeapp.data.database.dao.PlaylistItemDao
import com.github.hadilq.youtubeapp.data.database.dao.PlaylistItemPageTokenDao
import com.github.hadilq.youtubeapp.data.database.dao.PlaylistPageTokenDao
import com.github.hadilq.youtubeapp.data.datasource.device.DeviceDataSource
import com.github.hadilq.youtubeapp.data.datasource.device.DeviceDataSourceImpl
import com.github.hadilq.youtubeapp.data.datasource.google.GoogleDataSource
import com.github.hadilq.youtubeapp.data.datasource.google.GoogleDataSourceImpl
import com.github.hadilq.youtubeapp.data.datasource.preference.PreferencesDataSource
import com.github.hadilq.youtubeapp.data.datasource.preference.PreferencesDataSourceImpl
import com.github.hadilq.youtubeapp.data.datasource.youtube.YoutubeDataSource
import com.github.hadilq.youtubeapp.data.datasource.youtube.YoutubeDataSourceImpl
import com.github.hadilq.youtubeapp.data.paging.PlaylistItemRemoteMediator
import com.github.hadilq.youtubeapp.data.paging.PlaylistRemoteMediator
import com.github.hadilq.youtubeapp.domain.entity.Playlist
import com.google.android.gms.common.GoogleApiAvailability
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube

abstract class AbstractDataModule : DataModule {

  override val preferencesDataSource: PreferencesDataSource by lazy { PreferencesDataSourceImpl() }

  override val youtubeDataSource: YoutubeDataSource by lazy { YoutubeDataSourceImpl() }

  override val googleDataSource: GoogleDataSource by lazy { GoogleDataSourceImpl() }

  override val deviceDataSource: DeviceDataSource by lazy { DeviceDataSourceImpl() }

  override val connectivityManager: ConnectivityManager by lazy {
    applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  }

  override val googleApiAvailability: GoogleApiAvailability
    get() = GoogleApiAvailability.getInstance()

  override val youtube: YouTube
    get() {
      val transport = NetHttpTransport()
      val jsonFactory = JacksonFactory.getDefaultInstance()
      return YouTube.Builder(transport, jsonFactory, googleAccountCredential)
        .setApplicationName("Youtube App")
        .build()
    }

  override val db: AppDatabase by lazy {
    Room.databaseBuilder(
      applicationContext,
      AppDatabase::class.java, "youtube-app.db"
    ).build()
  }

  override val playlistDao: PlaylistDao by lazy {
    db.getPlaylistDao()
  }

  override val playlistPageTokenDao: PlaylistPageTokenDao by lazy {
    db.getPlaylistPageTokenDao()
  }

  override val playlistItemDao: PlaylistItemDao by lazy {
    db.getPlaylistItemDao()
  }

  override val playlistItemPageTokenDao: PlaylistItemPageTokenDao by lazy {
    db.getPlaylistItemPageTokenDao()
  }

  override fun playlistRemoteMediator(query: String?, pageSize: Int): PlaylistRemoteMediator =
    PlaylistRemoteMediator(query, pageSize, this)

  override fun playlistItemRemoteMediator(playlist: Playlist, pageSize: Int): PlaylistItemRemoteMediator =
    PlaylistItemRemoteMediator(playlist, pageSize, this)
}
