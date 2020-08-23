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
  id("com.android.library")
  kotlin("android")
  kotlin("android.extensions")
  kotlin("kapt")
  id("de.mannodermaus.android-junit5")
  id("com.github.hadilq.build-plugin")
}

android {
  compileSdkVersion(VERSION_COMPILE_SDK)
  defaultConfig {
    minSdkVersion(VERSION_MIN_SDK)
    targetSdkVersion(VERSION_TARGET_SDK)
    consumerProguardFiles("consumer-rules.pro")
    multiDexEnabled = true
  }

  compileOptions {
    sourceCompatibility(JavaVersion.VERSION_1_8)
    targetCompatibility(JavaVersion.VERSION_1_8)
  }

  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
  }
  lintOptions {
    disable("TrustAllX509TrustManager")
  }
}

addAndroidBasics()
addJUnit()

dependencies {
  implementation(project(":domain"))

  implementation(kotlin(KOTLIN_STDLIB))
  implementation(GOOGLE_PLAY_SERVICES)
  implementation(GOOGLE_PLAY_CLIENT) {
    exclude(group = APACHE_HTTP_GROUP_ID)
  }
  implementation(GOOGLE_YOUTUBE_API) {
    exclude(group = APACHE_HTTP_GROUP_ID)
  }
  implementation(COROUTINES)
  implementation(COROUTINES_ANDROID)
  implementation(PAGING_RUNTIME)
  implementation(ROOM_RUNTIME)
  kapt(ROOM_COMPILER)
  implementation(ROOM_KTX)
  implementation(MULTIDEX)
  implementation(COIL)

  testImplementation(PAGING_COMMON)
  testImplementation(ROOM_TESTING)
  testImplementation(MOCKK)
  testImplementation(COROUTINES_TEST)
}
