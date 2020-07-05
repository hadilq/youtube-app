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
package com.github.hadilq.youtubeapp.domain.entity

import java.util.Date

data class PlaylistList(
  val nextPageToken: String?,
  val pageInfo: PageInfo,
  val items: List<Playlist>
)

data class PageInfo(
  val totalResults: Int,
  val resultsPerPage: Int
)

@OptIn(ExperimentalUnsignedTypes::class)
data class Playlist(
  val id: String,
  val publishedAt: Date,
  val title: String,
  val thumbnail: Thumbnail,
  val channelTitle: String,
  val numberOfVideos: UInt?
)

@OptIn(ExperimentalUnsignedTypes::class)
data class Thumbnail(
  val url: String,
  val width: UInt,
  val height: UInt
)
