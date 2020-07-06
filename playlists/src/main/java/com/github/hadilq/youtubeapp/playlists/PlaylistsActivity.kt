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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.github.hadilq.androidlifecyclehandler.provideLife
import com.github.hadilq.coroutinelifecyclehandler.observe
import com.github.hadilq.youtubeapp.domain.di.App
import com.github.hadilq.youtubeapp.playlists.di.PlaylistsModule
import com.github.hadilq.youtubeapp.playlists.di.fix
import kotlinx.android.synthetic.main.playlists.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class PlaylistsActivity : AppCompatActivity() {

  private val module: PlaylistsModule by lazy { (application as App).appComponent.playlistsModule.fix() }

  private lateinit var viewModel: PlaylistsViewModel

  private lateinit var adapter: PlaylistsAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.playlists)
    viewModel = provideLife(module.playlistsViewModelFactory)

    with(viewModel) {
      playlists.observe()() { adapter.submitData(it) }
    }

    setupRecycler()

    loadPlaylists()
  }

  private fun setupRecycler() {
    list.layoutManager = GridLayoutManager(this, 2)
    adapter = module.playlistsAdapter
    list.adapter = adapter
  }

  private fun loadPlaylists() {
    module.run { viewModel.run { startLoading() } }
  }
}
