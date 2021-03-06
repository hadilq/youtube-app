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
package com.github.hadilq.youtubeapp.data.datasource.youtube

import com.github.hadilq.youtubeapp.data.di.DataModule
import com.github.hadilq.youtubeapp.domain.entity.AccountName
import com.github.hadilq.youtubeapp.domain.entity.Channel
import com.github.hadilq.youtubeapp.domain.entity.Intent
import com.github.hadilq.youtubeapp.domain.entity.Playlist
import com.github.hadilq.youtubeapp.domain.entity.PlaylistItems
import com.github.hadilq.youtubeapp.domain.entity.PlaylistList
import com.github.hadilq.youtubeapp.domain.entity.Query

interface YoutubeDataSource {

  suspend fun DataModule.getSelectedAccountName(): AccountName?

  suspend fun DataModule.setSelectedAccountName(accountName: AccountName?)

  suspend fun DataModule.newChooseAccountIntent(): Intent

  suspend fun DataModule.loadChannels(): List<Channel>

  suspend fun DataModule.playLists(
    query: Query? = null,
    pageSize: Int,
    pageNextToken: String? = null
  ): PlaylistList

  suspend fun DataModule.playListItems(
    playlist: Playlist,
    pageSize: Int,
    pageNextToken: String? = null
  ): PlaylistItems
}
