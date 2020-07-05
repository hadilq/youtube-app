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
package com.github.hadilq.youtubeapp.domain.repository

import androidx.paging.PagingData
import com.github.hadilq.youtubeapp.domain.di.DomainModule
import com.github.hadilq.youtubeapp.domain.entity.AccountName
import com.github.hadilq.youtubeapp.domain.entity.Intent
import com.github.hadilq.youtubeapp.domain.entity.Playlist
import com.github.hadilq.youtubeapp.domain.entity.PlaylistItem
import com.github.hadilq.youtubeapp.domain.entity.Query
import kotlinx.coroutines.flow.Flow

interface YoutubeRepository {

  fun DomainModule.getSelectedAccountName(): AccountName?

  fun DomainModule.setSelectedAccountName(accountName: AccountName)

  fun DomainModule.newChooseAccountIntent(): Intent

  fun DomainModule.startLoadingPlaylist(query: Query? = null): Flow<PagingData<Playlist>>

  fun DomainModule.startLoadingPlaylistItem(playlist: Playlist): Flow<PagingData<PlaylistItem>>
}
