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
import com.github.hadilq.youtubeapp.AppImpl
import com.github.hadilq.youtubeapp.data.repository.DeviceRepositoryImpl
import com.github.hadilq.youtubeapp.data.repository.GooglePlayRepositoryImpl
import com.github.hadilq.youtubeapp.data.repository.YoutubeRepositoryImpl
import com.github.hadilq.youtubeapp.domain.di.AppComponent
import com.github.hadilq.youtubeapp.domain.di.LoginModuleSyntax
import com.github.hadilq.youtubeapp.domain.di.PlaylistsModuleSyntax
import com.github.hadilq.youtubeapp.domain.repository.DeviceRepository
import com.github.hadilq.youtubeapp.domain.repository.GooglePlayRepository
import com.github.hadilq.youtubeapp.domain.repository.YoutubeRepository

class AppComponentImpl(
  private val application: AppImpl
) : AppComponent {

  /**
   * All singletons must be define inside [appModule]
   */
  private val appModule = object : AppModule {

    override val applicationContext: Context = application.applicationContext

    override val youtubeRepository: YoutubeRepository = YoutubeRepositoryImpl()

    override val googlePlayRepository: GooglePlayRepository = GooglePlayRepositoryImpl()

    override val deviceRepository: DeviceRepository = DeviceRepositoryImpl()
  }

  override val loginModule: LoginModuleSyntax
    get() {
      val domainModule = DomainModuleImpl(appModule)
      return AllNeedsOfLoginModule(
        appModule = appModule,
        domainModule = domainModule,
        dataModule = DataModuleImpl(appModule),
        loginModule = LoginModuleImpl(domainModule)
      )
    }

  override val playlistsModule: PlaylistsModuleSyntax
    get() {
      val domainModule = DomainModuleImpl(appModule)
      return AllNeedsOfPlaylistsModule(
        appModule = appModule,
        domainModule = domainModule,
        dataModule = DataModuleImpl(appModule),
        playlistsModule = PlaylistsModuleImpl(domainModule)
      )
    }
}
