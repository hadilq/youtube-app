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
import com.github.hadilq.youtubeapp.data.database.entity.Playlist

@Dao
interface PlaylistDao {

  @Query("SELECT * FROM playlist")
  fun getPlaylists(): PagingSource<Int, Playlist>

  @Insert
  suspend fun insertAll(vararg playlists: Playlist)

  @Delete
  suspend fun delete(playlist: Playlist)

  @Query("DELETE FROM playlist WHERE `query` = :query")
  suspend fun deleteByQuery(query: String?)
}
