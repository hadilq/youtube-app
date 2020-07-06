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

import com.github.hadilq.youtubeapp.NavigatorImpl
import com.github.hadilq.youtubeapp.domain.di.AbstractDomainModule
import com.github.hadilq.youtubeapp.domain.navigation.Navigator
import com.github.hadilq.youtubeapp.domain.repository.DeviceRepository
import com.github.hadilq.youtubeapp.domain.repository.GooglePlayRepository
import com.github.hadilq.youtubeapp.domain.repository.YoutubeRepository

class DomainModuleImpl(
  private val appModule: AppModule
) : AbstractDomainModule() {

  override val navigator: Navigator
    get() = NavigatorImpl()

  override val youtubeRepository: YoutubeRepository
    get() = appModule.youtubeRepository

  override val googlePlayRepository: GooglePlayRepository
    get() = appModule.googlePlayRepository

  override val deviceRepository: DeviceRepository
    get() = appModule.deviceRepository
}
