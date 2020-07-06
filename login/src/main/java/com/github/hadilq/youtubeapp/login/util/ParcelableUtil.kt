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
package com.github.hadilq.youtubeapp.login.util

import android.os.Parcel
import android.os.Parcelable

class ParcelableUtil {

  fun marshall(parcelable: Parcelable): ByteArray {
    val parcel = Parcel.obtain()
    parcelable.writeToParcel(parcel, 0)
    val bytes = parcel.marshall()
    parcel.recycle()
    return bytes
  }

  fun <T : Parcelable> unmarshall(bytes: ByteArray, creator: Parcelable.Creator<T>): T {
    val parcel = unmarshall(bytes)
    return creator.createFromParcel(parcel)
  }

  private fun unmarshall(bytes: ByteArray): Parcel {
    val parcel = Parcel.obtain()
    parcel.unmarshall(bytes, 0, bytes.size)
    parcel.setDataPosition(0) // this is extremely important!
    return parcel
  }
}
