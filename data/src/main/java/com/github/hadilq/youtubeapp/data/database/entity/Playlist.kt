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
package com.github.hadilq.youtubeapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@OptIn(ExperimentalUnsignedTypes::class)
@Entity(tableName = "playlist")
data class Playlist(
  @PrimaryKey(autoGenerate = true) val _id: Int = 0,
  @ColumnInfo(name = "id") val id: String,
  @ColumnInfo(name = "query") val query: String?,
  @ColumnInfo(name = "published_at") val publishedAt: Date,
  @ColumnInfo(name = "title") val title: String,
  @Embedded val thumbnail: Thumbnail,
  @ColumnInfo(name = "channel_title") val channelTitle: String,
  @ColumnInfo(name = "number_of_videos") val numberOfVideos: Int?
)

@OptIn(ExperimentalUnsignedTypes::class)
data class Thumbnail(
  @ColumnInfo(name = "url") val url: String,
  @ColumnInfo(name = "width") val width: Int,
  @ColumnInfo(name = "height") val height: Int
)
