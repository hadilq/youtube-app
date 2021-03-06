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

setupAndroidLibrary()

dependencies {
  implementation(project(":domain"))
  implementation(project(":core"))
  implementation(project(":presentation"))

  implementation(kotlin(KOTLIN_STDLIB))
  implementation(GOOGLE_PLAY_SERVICES)
  implementation(COROUTINES_LIFECYCLE)
  implementation(CONSTRAIN_LAYOUT)
  implementation(EASY_PERMISSION)
  implementation(COROUTINES)
  implementation(COROUTINES_ANDROID)
  implementation(COIL)

  testImplementation(MOCKK)
  testImplementation (COROUTINES_TEST)
}
