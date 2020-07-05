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
package com.github.hadilq.youtubeapp.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.github.hadilq.youtubeapp.data.database.entity.PlaylistItem

@Dao
interface PlaylistItemDao {

  @Query("SELECT * FROM playlist_item WHERE playlist_id=:playlistId")
  fun getPlaylistItems(playlistId: String): PagingSource<Int, PlaylistItem>

  @Insert
  suspend fun insertAll(vararg playlistItems: PlaylistItem)

  @Delete
  suspend fun delete(playlistItem: PlaylistItem)

  @Query("DELETE FROM playlist_item WHERE playlist_id=:playlistId")
  suspend fun deleteByPlaylistId(playlistId: String)
}
