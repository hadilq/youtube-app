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
package com.github.hadilq.youtubeapp.data.api.youtubeapi

import com.github.hadilq.youtubeapp.domain.entity.PlaylistItems
import com.github.hadilq.youtubeapp.domain.entity.PlaylistList
import com.google.api.services.youtube.model.PageInfo
import com.google.api.services.youtube.model.Playlist
import com.google.api.services.youtube.model.PlaylistItem
import com.google.api.services.youtube.model.PlaylistItemListResponse
import com.google.api.services.youtube.model.PlaylistListResponse
import com.google.api.services.youtube.model.SearchListResponse
import com.google.api.services.youtube.model.SearchResult
import com.google.api.services.youtube.model.Thumbnail
import com.google.api.services.youtube.model.VideoListResponse
import java.util.Date

fun PlaylistListResponse.map(): PlaylistList = PlaylistList(
  nextPageToken = nextPageToken,
  pageInfo = pageInfo.map(),
  items = items.map(Playlist::map)
)

private fun PageInfo.map() = com.github.hadilq.youtubeapp.domain.entity.PageInfo(
  totalResults = totalResults,
  resultsPerPage = resultsPerPage
)

@OptIn(ExperimentalUnsignedTypes::class)
private fun Playlist.map(): com.github.hadilq.youtubeapp.domain.entity.Playlist =
  com.github.hadilq.youtubeapp.domain.entity.Playlist(
    id = id,
    publishedAt = Date(snippet.publishedAt.value),
    title = snippet.title,
    thumbnail = snippet.thumbnails.medium.map(),
    channelTitle = snippet.channelTitle,
    numberOfVideos = contentDetails.itemCount.toUInt()
  )

@OptIn(ExperimentalUnsignedTypes::class)
private fun Thumbnail.map(): com.github.hadilq.youtubeapp.domain.entity.Thumbnail =
  com.github.hadilq.youtubeapp.domain.entity.Thumbnail(
    url = url,
    width = width.toUInt(),
    height = height.toUInt()
  )

fun PlaylistItemListResponse.map(
  playList: com.github.hadilq.youtubeapp.domain.entity.Playlist,
  videoDetails: List<VideoDetail>
): PlaylistItems =
  PlaylistItems(
    title = playList.title,
    nextPageToken = nextPageToken,
    numberOfVideos = playList.numberOfVideos,
    thumbnail = playList.thumbnail,
    items = items.zip(videoDetails).map { (playlistItem, detail) -> playlistItem.map(detail) }
  )

private fun PlaylistItem.map(
  videoDetails: VideoDetail
): com.github.hadilq.youtubeapp.domain.entity.PlaylistItem =
  com.github.hadilq.youtubeapp.domain.entity.PlaylistItem(
    title = snippet.title,
    thumbnail = snippet.thumbnails.medium.map(),
    author = videoDetails.author,
    duration = videoDetails.duration
  )

fun VideoListResponse.map(): VideoDetail {
  val item = items[0]
  return VideoDetail(
    author = item.contentDetails.caption,
    duration = item.contentDetails.duration
  )
}

data class VideoDetail(
  val author: String,
  val duration: String
)

fun SearchListResponse.map() :PlaylistList= PlaylistList(
  nextPageToken = nextPageToken,
  pageInfo = pageInfo.map(),
  items = items.map(SearchResult::map)
)

@OptIn(ExperimentalUnsignedTypes::class)
private fun SearchResult.map(): com.github.hadilq.youtubeapp.domain.entity.Playlist =
  com.github.hadilq.youtubeapp.domain.entity.Playlist(
    id = id.playlistId,
    publishedAt = Date(snippet.publishedAt.value),
    title = snippet.title,
    thumbnail = snippet.thumbnails.medium.map(),
    channelTitle = snippet.channelTitle,
    numberOfVideos = null
  )
