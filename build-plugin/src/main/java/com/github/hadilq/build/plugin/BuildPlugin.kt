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
package com.github.hadilq.build.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class BuildPlugin : Plugin<Project> {

  override fun apply(target: Project) {
  }
}

private const val VERSION_ANDROIDX_CORE = "1.3.1"
private const val VERSION_ANDROIDX_APPCOMPAT = "1.2.0"
private const val VERSION_ANDROID_MATERIAL = "1.2.0"
private const val VERSION_GOOGLE_PLAY_SERVICES = "18.0.0"
private const val VERSION_COROUTINES = "1.3.8"
private const val VERSION_COROUTINES_LIFECYCLE = "0.4.1"
private const val VERSION_GOOGLE_PLAY_CLIENT = "1.30.9"
private const val VERSION_GOOGLE_API = "v3-rev99-1.17.0-rc"
private const val VERSION_PAGING = "3.0.0-alpha02"
private const val VERSION_ROOM = "2.3.0-alpha01"
private const val VERSION_JUNIT = "5.6.2"
private const val VERSION_MOCKK = "1.10.0"

const val APPLICATION_ID = "com.github.hadilq.youtubeapp"
const val VERSION_COMPILE_SDK = 30
const val VERSION_MIN_SDK = 16
const val VERSION_TARGET_SDK = 30

const val VERSION_CODE = 1
const val VERSION_NAME = "1.0.0"

const val KOTLIN_STDLIB = "stdlib"
const val KOTLIN_STDLIB_COMMON = "stdlib-common"
const val KOTLIN_TEST_COMMON = "test-common"
const val KOTLIN_TEST_JVM = "test-junit"
const val KOTLIN_TEST_ANNOTATIONS_COMMON = "test-annotations-common"

const val ANDROIDX_CORE = "androidx.core:core-ktx:$VERSION_ANDROIDX_CORE"
const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:$VERSION_ANDROIDX_APPCOMPAT"
const val ANDROID_MATERIAL = "com.google.android.material:material:$VERSION_ANDROID_MATERIAL"
const val GOOGLE_PLAY_SERVICES =
  "com.google.android.gms:play-services-auth:$VERSION_GOOGLE_PLAY_SERVICES"
const val GOOGLE_PLAY_CLIENT =
  "com.google.api-client:google-api-client-android:$VERSION_GOOGLE_PLAY_CLIENT"
const val GOOGLE_YOUTUBE_API = "com.google.apis:google-api-services-youtube:$VERSION_GOOGLE_API"
const val APACHE_HTTP_GROUP_ID = "org.apache.httpcomponents"
const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$VERSION_COROUTINES"
const val COROUTINES_COMMON =
  "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$VERSION_COROUTINES"
const val COROUTINES_ANDROID =
  "org.jetbrains.kotlinx:kotlinx-coroutines-android:$VERSION_COROUTINES"
const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$VERSION_COROUTINES"
const val JUPITER_API = "org.junit.jupiter:junit-jupiter-api:$VERSION_JUNIT"
const val JUPITER_ENGINE = "org.junit.jupiter:junit-jupiter-engine:$VERSION_JUNIT"
const val COROUTINES_LIFECYCLE =
  "com.github.hadilq:coroutines-lifecycle-handler-android:$VERSION_COROUTINES_LIFECYCLE"
const val COROUTINES_LIFECYCLE_JVM =
  "com.github.hadilq:coroutines-lifecycle-handler-jvm:$VERSION_COROUTINES_LIFECYCLE"
const val COROUTINES_LIFECYCLE_COMMON =
  "com.github.hadilq:coroutines-lifecycle-handler-metadata:$VERSION_COROUTINES_LIFECYCLE"
const val CONSTRAIN_LAYOUT = "androidx.constraintlayout:constraintlayout:2.0.0-beta7"
const val EASY_PERMISSION = "pub.devrel:easypermissions:0.3.0"
const val PAGING_RUNTIME = "androidx.paging:paging-runtime:$VERSION_PAGING"
const val PAGING_COMMON = "androidx.paging:paging-common:$VERSION_PAGING"
const val ROOM_RUNTIME = "androidx.room:room-runtime:$VERSION_ROOM"
const val ROOM_COMPILER = "androidx.room:room-compiler:$VERSION_ROOM"
const val ROOM_KTX = "androidx.room:room-ktx:$VERSION_ROOM"
const val ROOM_TESTING = "androidx.room:room-testing:$VERSION_ROOM"
const val MULTIDEX = "androidx.multidex:multidex:2.0.1"
const val COIL = "io.coil-kt:coil-base:0.11.0"
const val MOCKK = "io.mockk:mockk:$VERSION_MOCKK"
const val MOCKK_COMMON = "io.mockk:mockk-common:$VERSION_MOCKK"
