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
import com.github.hadilq.youtubeapp.domain.repository.DeviceRepository
import com.github.hadilq.youtubeapp.domain.repository.GooglePlayRepository
import com.github.hadilq.youtubeapp.domain.repository.YoutubeRepository
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential

/**
 * All singletons must be define inside [AppModule]
 */
interface AppModule {

  val applicationContext: Context

  val googleAccountCredential: GoogleAccountCredential

  val youtubeRepository: YoutubeRepository

  val googlePlayRepository: GooglePlayRepository

  val deviceRepository: DeviceRepository

  val imageLoader: ImageLoader
}
