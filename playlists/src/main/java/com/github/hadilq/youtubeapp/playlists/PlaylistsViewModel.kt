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

import androidx.paging.PagingData
import com.github.hadilq.androidlifecyclehandler.LifeFactory
import com.github.hadilq.androidlifecyclehandler.SLife
import com.github.hadilq.youtubeapp.core.util.execute
import com.github.hadilq.youtubeapp.domain.entity.Playlist
import com.github.hadilq.youtubeapp.playlists.di.PlaylistsModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow

@OptIn(ExperimentalCoroutinesApi::class)
class PlaylistsViewModel : SLife() {

  private val playlistsPublisher = Channel<PagingData<Playlist>>(Channel.CONFLATED)

  val playlists = playlistsPublisher.receiveAsFlow()

  fun PlaylistsModule.startLoading() = execute {
    getPlaylists.run { execute(null) }.collect {
      playlistsPublisher.send(it)
    }
  }.sync()
}

class PlaylistsViewModelFactory : LifeFactory<PlaylistsViewModel> {

  override fun get(): PlaylistsViewModel = PlaylistsViewModel()
}
