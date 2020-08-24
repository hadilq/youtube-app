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

import com.github.hadilq.youtubeapp.domain.entity.Channel
import com.github.hadilq.youtubeapp.domain.entity.PageInfo
import com.github.hadilq.youtubeapp.domain.entity.Playlist
import com.github.hadilq.youtubeapp.domain.entity.PlaylistItem
import com.github.hadilq.youtubeapp.domain.entity.PlaylistItems
import com.github.hadilq.youtubeapp.domain.entity.PlaylistList
import com.github.hadilq.youtubeapp.domain.entity.Thumbnail
import com.google.api.services.youtube.model.ChannelListResponse
import com.google.api.services.youtube.model.PlaylistItemListResponse
import com.google.api.services.youtube.model.PlaylistListResponse
import com.google.api.services.youtube.model.SearchListResponse
import com.google.api.services.youtube.model.SearchResult
import com.google.api.services.youtube.model.VideoListResponse
import com.google.api.services.youtube.model.Channel as ApiChannel
import com.google.api.services.youtube.model.PageInfo as ApiPageInfo
import com.google.api.services.youtube.model.Playlist as ApiPlaylist
import com.google.api.services.youtube.model.PlaylistItem as ApiPlaylistItem
import com.google.api.services.youtube.model.Thumbnail as ApiThumbnail

fun PlaylistListResponse.map(): PlaylistList = PlaylistList(
  nextPageToken = nextPageToken,
  pageInfo = pageInfo.map(),
  items = items.map(ApiPlaylist::map)
)

private fun ApiPageInfo.map() = PageInfo(
  totalResults = totalResults,
  resultsPerPage = resultsPerPage
)

@OptIn(ExperimentalUnsignedTypes::class)
private fun ApiPlaylist.map(): Playlist = Playlist(
  id = id,
  publishedAt = snippet.publishedAt.value,
  title = snippet.title,
  thumbnail = snippet.thumbnails.medium.map(),
  channelTitle = snippet.channelTitle,
  numberOfVideos = contentDetails.itemCount.toUInt()
)

@OptIn(ExperimentalUnsignedTypes::class)
private fun ApiThumbnail.map(): Thumbnail = Thumbnail(
  url = url,
  width = width.toUInt(),
  height = height.toUInt()
)

fun PlaylistItemListResponse.map(
  playList: Playlist,
  videoDetails: List<VideoDetail>
): PlaylistItems =
  PlaylistItems(
    title = playList.title,
    nextPageToken = nextPageToken,
    numberOfVideos = playList.numberOfVideos,
    thumbnail = playList.thumbnail,
    items = items.zip(videoDetails).map { (playlistItem, detail) -> playlistItem.map(detail) }
  )

private fun ApiPlaylistItem.map(
  videoDetails: VideoDetail
): PlaylistItem = PlaylistItem(
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

fun SearchListResponse.map(): PlaylistList = PlaylistList(
  nextPageToken = nextPageToken,
  pageInfo = pageInfo.map(),
  items = items.map(SearchResult::map)
)

@OptIn(ExperimentalUnsignedTypes::class)
private fun SearchResult.map(): Playlist = Playlist(
  id = id.playlistId,
  publishedAt = snippet.publishedAt.value,
  title = snippet.title,
  thumbnail = snippet.thumbnails.medium.map(),
  channelTitle = snippet.channelTitle,
  numberOfVideos = null
)

fun ChannelListResponse.map(): Sequence<Channel> = items?.asSequence()?.map(ApiChannel::map) ?: emptySequence()

fun ApiChannel.map(): Channel = Channel(
  id = id,
  title = snippet.title
)
