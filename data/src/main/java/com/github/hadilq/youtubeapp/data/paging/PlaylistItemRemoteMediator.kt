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
package com.github.hadilq.youtubeapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.github.hadilq.youtubeapp.data.database.entity.PlaylistItem
import com.github.hadilq.youtubeapp.data.database.entity.PlaylistItemPageToken
import com.github.hadilq.youtubeapp.data.database.map
import com.github.hadilq.youtubeapp.data.di.DataModule
import com.github.hadilq.youtubeapp.domain.entity.GoogleAuthIOError
import com.github.hadilq.youtubeapp.domain.entity.GooglePlayServicesAvailabilityError
import com.github.hadilq.youtubeapp.domain.entity.PlaylistItems
import com.github.hadilq.youtubeapp.domain.entity.UserRecoverableAuthIOError
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAuthIOException
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException

@OptIn(ExperimentalPagingApi::class)
class PlaylistItemRemoteMediator(
  private val playlist: com.github.hadilq.youtubeapp.domain.entity.Playlist,
  private val pageSize: Int,
  private val module: DataModule
) : RemoteMediator<Int, PlaylistItem>() {

  override suspend fun load(loadType: LoadType, state: PagingState<Int, PlaylistItem>): MediatorResult =
    module.load(loadType, state)

  private suspend fun DataModule.load(
    loadType: LoadType,
    @Suppress("UNUSED_PARAMETER") state: PagingState<Int, PlaylistItem>
  ): MediatorResult {
    return try {
      val loadKey = when (loadType) {
        LoadType.REFRESH -> null
        LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
        LoadType.APPEND -> {
          val remoteKey = db.withTransaction {
            playlistItemPageTokenDao.getToken(playlist.id)
          }

          if (remoteKey.nextPageToken == null) {
            return MediatorResult.Success(endOfPaginationReached = true)
          }

          remoteKey.nextPageToken
        }
      }

      val response: PlaylistItems = youtubeDataSource.run { playListItems(playlist, pageSize, loadKey) }

      db.withTransaction {
        if (loadType == LoadType.REFRESH) {
          playlistItemPageTokenDao.deleteByPlaylistId(playlist.id)
          playlistItemDao.deleteByPlaylistId(playlist.id)
        }
        playlistItemPageTokenDao.insertOrReplace(
          PlaylistItemPageToken(playlistId = playlist.id, nextPageToken = response.nextPageToken)
        )

        playlistItemDao.insertAll(*response.items.map { it.map(playlist) }.toTypedArray())
      }

      MediatorResult.Success(endOfPaginationReached = response.nextPageToken == null)
    } catch (e: GooglePlayServicesAvailabilityIOException) {
      youtubeRepository.run { publishError(GooglePlayServicesAvailabilityError(e, e.intent)) }
      MediatorResult.Error(e)
    } catch (e: UserRecoverableAuthIOException) {
      youtubeRepository.run { publishError(UserRecoverableAuthIOError(e, e.intent)) }
      MediatorResult.Error(e)
    } catch (e: GoogleAuthIOException) {
      youtubeRepository.run { publishError(GoogleAuthIOError(e)) }
      MediatorResult.Error(e)
    } catch (e: Throwable) {
      MediatorResult.Error(e)
    }
  }
}
