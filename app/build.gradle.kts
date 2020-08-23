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
import com.github.hadilq.build.plugin.*

plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("android.extensions")
  kotlin("kapt")
  id("de.mannodermaus.android-junit5")
  id("com.github.hadilq.build-plugin")
}

android {
  compileSdkVersion(VERSION_COMPILE_SDK)
  defaultConfig {
    applicationId = APPLICATION_ID
    minSdkVersion(VERSION_MIN_SDK)
    targetSdkVersion(VERSION_TARGET_SDK)
    multiDexEnabled = true
    versionCode = VERSION_CODE
    versionName = VERSION_NAME
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  setupJavaTarget()
}

addAndroidBasics()
addJUnit()

dependencies {
  implementation(project(":domain"))
  implementation(project(":data"))
  implementation(project(":core"))
  implementation(project(":login"))
  implementation(project(":playlists"))

  implementation(kotlin(KOTLIN_STDLIB))
  implementation(COROUTINES)
  implementation(COROUTINES_ANDROID)
  implementation(GOOGLE_PLAY_CLIENT) {
    exclude(group = APACHE_HTTP_GROUP_ID)
  }
  implementation(GOOGLE_YOUTUBE_API) {
    exclude(group = APACHE_HTTP_GROUP_ID)
  }
  implementation(COIL)
}
