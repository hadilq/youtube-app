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
plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("android.extensions")
  kotlin("kapt")
}
val versionKotlin: String by project
val versionCompileSdk: String by project
val versionMinSdk: String by project
val versionTargetSdk: String by project
val versionAndroidxCore: String by project
val versionAndroidxAppcompat: String by project
val versionComGoogleAndroidMaterial: String by project
val versionJunit: String by project
val versionAndroidxTestExt: String by project
val versionAndroidxTestEspresso: String by project
val versionCoroutines: String by project
val versionComGoogleApiClient: String by project
val versionComGoogleApis: String by project

android {
  compileSdkVersion(versionCompileSdk.toInt())
  defaultConfig {
    applicationId = "com.github.hadilq.youtubeapp"
    minSdkVersion(versionMinSdk.toInt())
    targetSdkVersion(versionTargetSdk.toInt())
    multiDexEnabled = true
    versionCode = 1
    versionName = "1.0"
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
}

dependencies {
//    kapt(Depends.lifecycleCompiler)

  implementation(project(":domain"))
  implementation(project(":data"))
  implementation(project(":core"))
  implementation(project(":login"))
  implementation(project(":playlists"))

  implementation("org.jetbrains.kotlin:kotlin-stdlib:$versionKotlin")
  implementation("androidx.core:core-ktx:$versionAndroidxCore")
  implementation("androidx.appcompat:appcompat:$versionAndroidxAppcompat")
  implementation("com.google.android.material:material:$versionComGoogleAndroidMaterial")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$versionCoroutines")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${versionCoroutines}")
  implementation("com.google.api-client:google-api-client-android:$versionComGoogleApiClient") {
    exclude(group = "org.apache.httpcomponents")
  }
  implementation("com.google.apis:google-api-services-youtube:$versionComGoogleApis") {
    exclude(group = "org.apache.httpcomponents")
  }

  testImplementation("org.junit.jupiter:junit-jupiter-api:$versionJunit")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$versionJunit")
  androidTestImplementation("androidx.test.ext:junit:$versionAndroidxTestExt")
  androidTestImplementation("androidx.test.espresso:espresso-core:$versionAndroidxTestEspresso")
}
