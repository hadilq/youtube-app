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

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.hadilq.youtubeapp.data.database.dao.PlaylistDao
import com.github.hadilq.youtubeapp.data.database.dao.PlaylistItemDao
import com.github.hadilq.youtubeapp.data.database.dao.PlaylistItemPageTokenDao
import com.github.hadilq.youtubeapp.data.database.dao.PlaylistPageTokenDao
import com.github.hadilq.youtubeapp.data.database.entity.Playlist
import com.github.hadilq.youtubeapp.data.database.entity.PlaylistItem
import com.github.hadilq.youtubeapp.data.database.entity.PlaylistItemPageToken
import com.github.hadilq.youtubeapp.data.database.entity.PlaylistPageToken

@Database(
  entities = [
    Playlist::class,
    PlaylistPageToken::class,
    PlaylistItem::class,
    PlaylistItemPageToken::class
  ],
  version = 1,
  exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {

  abstract fun getPlaylistDao(): PlaylistDao

  abstract fun getPlaylistPageTokenDao(): PlaylistPageTokenDao

  abstract fun getPlaylistItemDao(): PlaylistItemDao

  abstract fun getPlaylistItemPageTokenDao(): PlaylistItemPageTokenDao
}
