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
package com.github.hadilq.youtubeapp.data.database

import com.github.hadilq.youtubeapp.domain.entity.Playlist
import com.github.hadilq.youtubeapp.domain.entity.PlaylistItem
import com.github.hadilq.youtubeapp.domain.entity.Thumbnail
import java.util.*
import com.github.hadilq.youtubeapp.data.database.entity.Playlist as PlaylistDB
import com.github.hadilq.youtubeapp.data.database.entity.PlaylistItem as PlaylistItemDB
import com.github.hadilq.youtubeapp.data.database.entity.Thumbnail as ThumbnailDB

@OptIn(ExperimentalUnsignedTypes::class)
fun Playlist.map(query: String?): PlaylistDB =
  PlaylistDB(
    id = id,
    query = query,
    publishedAt = Date(publishedAt),
    title = title,
    thumbnail = thumbnail.map(),
    channelTitle = channelTitle,
    numberOfVideos = numberOfVideos?.toInt()
  )

@OptIn(ExperimentalUnsignedTypes::class)
private fun Thumbnail.map(): ThumbnailDB =
  ThumbnailDB(
    url = url,
    width = width.toInt(),
    height = height.toInt()
  )

@OptIn(ExperimentalUnsignedTypes::class)
fun PlaylistDB.map(): Playlist = Playlist(
  id = id,
  publishedAt = publishedAt.time,
  title = title,
  thumbnail = thumbnail.map(),
  channelTitle = channelTitle,
  numberOfVideos = numberOfVideos?.toUInt()
)

@OptIn(ExperimentalUnsignedTypes::class)
private fun ThumbnailDB.map(): Thumbnail = Thumbnail(
  url = url,
  width = width.toUInt(),
  height = height.toUInt()
)

fun PlaylistItem.map(playlist: Playlist): PlaylistItemDB =
  PlaylistItemDB(
    playlistId = playlist.id,
    title = title,
    thumbnail = thumbnail.map(),
    author = author,
    duration = duration
  )

fun PlaylistItemDB.map(): PlaylistItem =
  PlaylistItem(
    title = title,
    thumbnail = thumbnail.map(),
    author = author,
    duration = duration
  )
