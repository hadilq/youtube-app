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
import com.github.hadilq.youtubeapp.domain.di.DataModuleSyntax
import com.github.hadilq.youtubeapp.domain.di.DomainModule
import com.github.hadilq.youtubeapp.domain.entity.AccountName
import com.github.hadilq.youtubeapp.domain.entity.Either
import com.github.hadilq.youtubeapp.domain.entity.Error
import com.github.hadilq.youtubeapp.domain.entity.GoogleAuthIOError
import com.github.hadilq.youtubeapp.domain.entity.GooglePlayServicesAvailabilityError
import com.github.hadilq.youtubeapp.domain.entity.Intent
import com.github.hadilq.youtubeapp.domain.entity.Left
import com.github.hadilq.youtubeapp.domain.entity.Query
import com.github.hadilq.youtubeapp.domain.entity.Right
import com.github.hadilq.youtubeapp.domain.entity.UserRecoverableAuthIOError
import com.github.hadilq.youtubeapp.domain.repository.YoutubeRepository
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAuthIOException
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import com.github.hadilq.youtubeapp.domain.entity.Channel as ChannelEntity
import com.github.hadilq.youtubeapp.domain.entity.Playlist as PlaylistEntity
import com.github.hadilq.youtubeapp.domain.entity.PlaylistItem as PlaylistItemEntity

@OptIn(ExperimentalCoroutinesApi::class)
class YoutubeRepositoryImpl : YoutubeRepository {

  private val errorPublisher = Channel<Error>()

  override suspend fun DomainModule.getSelectedAccountName(): AccountName? = with(fix()) {
    youtubeDataSource.run { getSelectedAccountName() }
      ?: (preferencesDataSource.run { readString(PREF_ACCOUNT_NAME, null) }
        ?.also { youtubeDataSource.run { setSelectedAccountName(it) } })
  }

  override suspend fun DomainModule.setSelectedAccountName(accountName: AccountName?) = with(fix()) {
    youtubeDataSource.run { setSelectedAccountName(accountName) }
    preferencesDataSource.run { writeString(PREF_ACCOUNT_NAME, accountName) }
  }

  override suspend fun DomainModule.newChooseAccountIntent(): Intent = with(fix()) {
    youtubeDataSource.run { newChooseAccountIntent() }
  }

  override suspend fun DomainModule.loadChannels(): Flow<Either<List<ChannelEntity>, Error>> = with(fix()) {
    flowOf(Left<List<ChannelEntity>, Error>(
      youtubeDataSource.run { loadChannels() }
    ) as Either<List<ChannelEntity>, Error>)
      .catch { e ->
        when (e) {
          is GooglePlayServicesAvailabilityIOException -> emit(Right(
            GooglePlayServicesAvailabilityError(e, e.intent)
          ))
          is UserRecoverableAuthIOException -> emit(Right(UserRecoverableAuthIOError(e, e.intent)))
          is GoogleAuthIOException -> emit(Right(GoogleAuthIOError(e)))
        }
      }
  }

  override suspend fun DomainModule.startLoadingPlaylist(
    query: Query?
  ): Flow<PagingData<PlaylistEntity>> = with(fix()) {
    getSelectedAccountName()
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
      getSelectedAccountName()
      val pageSize = 20
      Pager(
        config = PagingConfig(pageSize),
        remoteMediator = playlistItemRemoteMediator(playlist, pageSize)
      ) {
        playlistItemDao.getPlaylistItems(playlist.id)
      }.flow.map { data: PagingData<PlaylistItem> -> data.map { it.map() } }
    }

  override suspend fun DomainModule.handleErrors(): Flow<Error> = errorPublisher.receiveAsFlow()

  override suspend fun DataModuleSyntax.publishError(error: Error) = errorPublisher.send(error)
}

private const val PREF_ACCOUNT_NAME = "accountName"
