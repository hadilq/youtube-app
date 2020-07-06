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
package com.github.hadilq.youtubeapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.hadilq.youtubeapp.data.database.entity.Playlist
import com.github.hadilq.youtubeapp.data.database.entity.PlaylistItem
import com.github.hadilq.youtubeapp.data.database.map
import com.github.hadilq.youtubeapp.data.di.fix
import com.github.hadilq.youtubeapp.domain.di.DomainModule
import com.github.hadilq.youtubeapp.domain.entity.AccountName
import com.github.hadilq.youtubeapp.domain.entity.Intent
import com.github.hadilq.youtubeapp.domain.entity.Query
import com.github.hadilq.youtubeapp.domain.repository.YoutubeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.github.hadilq.youtubeapp.domain.entity.Playlist as PlaylistEntity
import com.github.hadilq.youtubeapp.domain.entity.PlaylistItem as PlaylistItemEntity

class YoutubeRepositoryImpl : YoutubeRepository {

  override suspend fun DomainModule.getSelectedAccountName(): AccountName? = with(fix()) {
    preferencesDataSource.run { readString(PREF_ACCOUNT_NAME, null) }
      ?: youtubeDataSource.run { getSelectedAccountName() }
  }

  override suspend fun DomainModule.setSelectedAccountName(accountName: AccountName) = with(fix()) {
    youtubeDataSource.run { setSelectedAccountName(accountName) }
    preferencesDataSource.run { writeString(PREF_ACCOUNT_NAME, accountName) }
  }

  override suspend fun DomainModule.newChooseAccountIntent(): Intent = with(fix()) {
    youtubeDataSource.run { newChooseAccountIntent() }
  }

  override suspend fun DomainModule.startLoadingPlaylist(
    query: Query?
  ): Flow<PagingData<PlaylistEntity>> = with(fix()) {
    val pageSize = 20
    Pager(
      config = PagingConfig(pageSize),
      remoteMediator = playlistRemoteMediator(query, pageSize)
    ) {
      playlistDao.getPlaylists()
    }.flow.map { data: PagingData<Playlist> -> data.map { it.map() } }
  }

  override suspend fun DomainModule.startLoadingPlaylistItem(
    playlist: PlaylistEntity
  ): Flow<PagingData<PlaylistItemEntity>> =
    with(fix()) {
      val pageSize = 20
      Pager(
        config = PagingConfig(pageSize),
        remoteMediator = playlistItemRemoteMediator(playlist, pageSize)
      ) {
        playlistItemDao.getPlaylistItems(playlist.id)
      }.flow.map { data: PagingData<PlaylistItem> -> data.map { it.map() } }
    }
}

private const val PREF_ACCOUNT_NAME = "accountName"
