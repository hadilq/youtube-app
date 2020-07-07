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
package com.github.hadilq.youtubeapp.di

import android.content.Context
import coil.ImageLoader
import com.github.hadilq.youtubeapp.core.di.CoreModule
import com.github.hadilq.youtubeapp.core.navigation.Navigator
import com.github.hadilq.youtubeapp.domain.di.DomainModule
import com.github.hadilq.youtubeapp.playlists.di.AbstractPlaylistsModule
import kotlinx.coroutines.CoroutineScope

class PlaylistsModuleImpl(
  private val domainModule: DomainModule,
  private val coreModule: CoreModule
) : AbstractPlaylistsModule(), DomainModule by domainModule {

  override fun navigator(context: Context): Navigator = coreModule.navigator(context)

  override val imageLoader: ImageLoader
    get() = coreModule.imageLoader

  override val viewModelScope: CoroutineScope
    get() = coreModule.viewModelScope
}
