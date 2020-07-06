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
import com.github.hadilq.youtubeapp.domain.entity.Intent
import com.github.hadilq.youtubeapp.domain.entity.Playlist
import com.github.hadilq.youtubeapp.domain.entity.PlaylistItems
import com.github.hadilq.youtubeapp.domain.entity.PlaylistList
import com.github.hadilq.youtubeapp.domain.entity.Query
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.PlaylistItemListResponse
import com.google.api.services.youtube.model.SearchListResponse
import com.google.api.services.youtube.model.VideoListResponse

@Suppress("BlockingMethodInNonBlockingContext")
class YoutubeDataSourceImpl : YoutubeDataSource {

  override suspend fun DataModule.getSelectedAccountName(): AccountName? = googleAccountCredential.selectedAccountName

  override suspend fun DataModule.setSelectedAccountName(accountName: AccountName) {
    googleAccountCredential.selectedAccountName = accountName
  }

  override suspend fun DataModule.newChooseAccountIntent(): Intent =
    dataParcelableUtil.marshall(googleAccountCredential.newChooseAccountIntent())

  override suspend fun DataModule.playLists(
    query: Query?,
    pageSize: Int,
    pageNextToken: String?
  ): PlaylistList = networkCall {
    query?.let { q ->
      val list = youtube.search().list("part=snippet")
      list.q = q
      list.type = "playlist"
      list.maxResults = pageSize.toLong()
      pageNextToken?.also { list.pageToken = it }
      val response: SearchListResponse = list.execute()
      response.items
      response.map()
    } ?: run {
      val list: YouTube.Playlists.List = youtube.playlists().list("part=snippet")
      list.maxResults = pageSize.toLong()
      pageNextToken?.also { list.pageToken = it }
      list.execute().map()
    }
  }

  override suspend fun DataModule.playListItems(
    playlist: Playlist,
    pageSize: Int,
    pageNextToken: String?
  ): PlaylistItems = networkCall {
    val list: YouTube.PlaylistItems.List = youtube.playlistItems().list("part=snippet")
    list.maxResults = pageSize.toLong()
    pageNextToken?.also { list.pageToken = it }
    val response: PlaylistItemListResponse = list.execute()
    val items = response.items.map {
      val videoList = youtube.videos().list("")
      videoList.id = it.id
      val execute: VideoListResponse = networkCall { videoList.execute() }
      execute.map()
    }
    response.map(playlist, items)
  }
}
