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

import com.github.hadilq.youtubeapp.domain.di.DataModuleSyntax
import com.github.hadilq.youtubeapp.domain.di.DomainModule
import com.github.hadilq.youtubeapp.domain.entity.*
import kotlinx.coroutines.flow.Flow

interface YoutubeRepository {

  suspend fun DomainModule.getSelectedAccountName(): AccountName?

  suspend fun DomainModule.setSelectedAccountName(accountName: AccountName?)

  suspend fun DomainModule.newChooseAccountIntent(): Intent

  suspend fun DomainModule.loadChannels(): Flow<Either<List<Channel>, Error>>

  suspend fun DomainModule.startLoadingPlaylist(query: Query? = null): Flow<PagingData<Playlist>>

  suspend fun DomainModule.startLoadingPlaylistItem(playlist: Playlist): Flow<PagingData<PlaylistItem>>

  suspend fun DomainModule.handleErrors(): Flow<Error>

  suspend fun DataModuleSyntax.publishError(error: Error)
}
