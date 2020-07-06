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
package com.github.hadilq.youtubeapp.playlists

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.hadilq.youtubeapp.domain.entity.Playlist
import com.github.hadilq.youtubeapp.playlists.di.PlaylistsModule

class PlaylistsAdapter(
  private val module: PlaylistsModule
) : PagingDataAdapter<Playlist, PlaylistViewHolder>(Diff()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
    val inflater: LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    return module.playlistViewHolderFactory.create(parent, inflater)
  }

  override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
    holder.onBind(getItem(position))
  }
}

class PlaylistViewHolder(parent: ViewGroup, inflater: LayoutInflater) :
  RecyclerView.ViewHolder(inflater.inflate(R.layout.playlist, parent)) {

  fun onBind(playlist: Playlist?) {

  }
}

class PlaylistViewHolderFactory {

  fun create(parent: ViewGroup, inflater: LayoutInflater) = PlaylistViewHolder(parent, inflater)
}

class Diff : DiffUtil.ItemCallback<Playlist>() {

  override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist): Boolean =
    oldItem == newItem

  override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist): Boolean =
    oldItem == newItem
}
