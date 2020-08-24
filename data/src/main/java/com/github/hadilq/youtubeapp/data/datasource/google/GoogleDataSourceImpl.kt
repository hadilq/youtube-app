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
package com.github.hadilq.youtubeapp.data.datasource.google

import com.github.hadilq.youtubeapp.data.di.DataModule
import com.github.hadilq.youtubeapp.domain.entity.ConnectionResult

class GoogleDataSourceImpl : GoogleDataSource {

  override suspend fun DataModule.isGooglePlayServicesAvailable(): ConnectionResult =
    googleApiAvailability.isGooglePlayServicesAvailable(applicationContext).toResult()

  override suspend fun DataModule.isUserResolvableError(connectionResult: ConnectionResult): Boolean =
    googleApiAvailability.isUserResolvableError(connectionResult.code)

  private fun Int.toResult(): ConnectionResult = when (this) {
    com.google.android.gms.common.ConnectionResult.SUCCESS -> ConnectionResult.Success(this)
    else -> ConnectionResult.Fail(this)
  }
}
