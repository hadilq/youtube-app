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
package com.github.hadilq.youtubeapp.data.api.youtubeapi.dto

import java.util.Date

data class PlaylistListDto(
  val nextPageToken: String,
  val prevPageToken: String,
  val pageInfo: PageInfoDto,
  val items: List<PlaylistDto>
)

data class PageInfoDto(
  val totalResults: Int,
  val resultsPerPage: Int
)

data class PlaylistDto(
  val id: String,
  val snippet: SnippetDto
)

data class ThumbnailsDto(
  val default: ThumbnailDto,
  val medium: ThumbnailDto,
  val high: ThumbnailDto,
  val standard: ThumbnailDto,
  val maxres: ThumbnailDto
)

data class ThumbnailDto(
  val url: String,
  val width: UInt,
  val height: UInt
)

data class LocalizedDto(
  val title: String,
  val description: String
)

data class SnippetDto(
  val publishedAt: Date,
  val channelId: String,
  val title: String,
  val description: String,
  val thumbnails: ThumbnailsDto,
  val channelTitle: String,
  val defaultLanguage: String,
  val localized: LocalizedDto
)

