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

@Entity(tableName = "playlist_item")
data class PlaylistItem(
  @PrimaryKey(autoGenerate = true) val _id: Int = 0,
  @ColumnInfo(name = "playlist_id") val playlistId: String,
  @ColumnInfo(name = "title") val title: String,
  @Embedded val thumbnail: Thumbnail,
  @ColumnInfo(name = "author") val author: String,
  @ColumnInfo(name = "duration") val duration: String

)
