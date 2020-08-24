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
package com.github.hadilq.youtubeapp.presentation.playlists

import androidx.paging.PagingData
import com.github.hadilq.androidlifecyclehandler.LifeFactory
import com.github.hadilq.androidlifecyclehandler.SLife
import com.github.hadilq.coroutinelifecyclehandler.execute
import com.github.hadilq.youtubeapp.domain.entity.GooglePlayServicesAvailabilityError
import com.github.hadilq.youtubeapp.domain.entity.Intent
import com.github.hadilq.youtubeapp.domain.entity.Playlist
import com.github.hadilq.youtubeapp.domain.entity.UserRecoverableAuthIOError
import com.github.hadilq.youtubeapp.presentation.di.PresentationModule
import com.github.hadilq.youtubeapp.presentation.di.fixDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

@OptIn(ExperimentalCoroutinesApi::class)
class PlaylistsViewModel : SLife() {

  private val playlistsPublisher = Channel<PagingData<Playlist>>(Channel.CONFLATED)
  private val navToLoginPublisher = Channel<Intent?>(Channel.CONFLATED)

  val playlists = playlistsPublisher.receiveAsFlow()
  val navToLogin = navToLoginPublisher.receiveAsFlow()

  fun PresentationModule.startWatchingForErrors() = execute(viewModelScope) {
    with(fixDomain()) {
      handleErrors.run { execute() }
        .onEach {
          when (it) {
            is UserRecoverableAuthIOError -> navToLoginPublisher.send(it.intent)
            is GooglePlayServicesAvailabilityError -> navToLoginPublisher.send(it.intent)
            else -> {
              setSelectedAccountName.run { execute(null) }
              navToLoginPublisher.send(null)
            }
          }
        }
        .launchIn(this@execute)
    }
  }.sync()

  fun PresentationModule.startLoading() = execute(viewModelScope) {
    with(fixDomain()) {
      getPlaylists.run { execute(null) }.collect {
        playlistsPublisher.send(it)
      }
    }
  }.sync()
}

class PlaylistsViewModelFactory : LifeFactory<PlaylistsViewModel> {

  override fun get(): PlaylistsViewModel = PlaylistsViewModel()
}
