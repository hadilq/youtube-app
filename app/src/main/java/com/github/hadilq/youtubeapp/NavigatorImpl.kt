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
package com.github.hadilq.youtubeapp

import android.content.Intent
import com.github.hadilq.youtubeapp.di.AppModule
import com.github.hadilq.youtubeapp.domain.navigation.Navigator
import com.github.hadilq.youtubeapp.domain.navigation.Page
import com.github.hadilq.youtubeapp.domain.navigation.Playlist
import com.github.hadilq.youtubeapp.playlists.PlaylistsActivity

class NavigatorImpl(
  private val appModule: AppModule
) : Navigator {

  override fun navigateTo(page: Page) = when (page) {
    Playlist -> appModule.applicationContext.startActivity(
      Intent(appModule.applicationContext, PlaylistsActivity::class.java)
    )
  }
}
