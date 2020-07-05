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
import com.github.hadilq.youtubeapp.data.api.youtubeapi.YoutubeDataSource
import com.github.hadilq.youtubeapp.data.database.AppDatabase
import com.github.hadilq.youtubeapp.data.database.dao.PlaylistDao
import com.github.hadilq.youtubeapp.data.database.dao.PlaylistItemDao
import com.github.hadilq.youtubeapp.data.database.dao.PlaylistItemPageTokenDao
import com.github.hadilq.youtubeapp.data.database.dao.PlaylistPageTokenDao
import com.github.hadilq.youtubeapp.data.paging.PlaylistItemRemoteMediator
import com.github.hadilq.youtubeapp.data.paging.PlaylistRemoteMediator
import com.github.hadilq.youtubeapp.data.repository.YoutubeRepositoryImpl
import com.github.hadilq.youtubeapp.data.util.ParcelableUtil
import com.github.hadilq.youtubeapp.domain.di.DataModuleSyntax
import com.github.hadilq.youtubeapp.domain.entity.Playlist
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.services.youtube.YouTube
import kotlinx.coroutines.CoroutineScope

interface DataModule : DataModuleSyntax {

  val context: Context

  val youtubeDataSource: YoutubeDataSource

  val youtubeRepositoryImpl: YoutubeRepositoryImpl

  val googleAccountCredential: GoogleAccountCredential

  val parcelableUtil: ParcelableUtil

  val youtube: YouTube

  val db: AppDatabase

  val playlistDao: PlaylistDao

  val playlistPageTokenDao: PlaylistPageTokenDao

  val playlistItemDao: PlaylistItemDao

  val playlistItemPageTokenDao: PlaylistItemPageTokenDao

  fun playlistRemoteMediator(query: String?, pageSize: Int): PlaylistRemoteMediator

  fun playlistItemRemoteMediator(playlist: Playlist, pageSize: Int): PlaylistItemRemoteMediator

  suspend fun <T> networkCall(block: suspend CoroutineScope.() -> T): T
}

fun DataModuleSyntax.fix() = this as DataModule
