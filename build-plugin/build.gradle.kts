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

buildscript {
  file("../gradle.properties").inputStream().let {
    val props = java.util.Properties()
    props.load(it)
    props.forEach { name, value ->
      extra["$name"] = value
    }
  }

  val versionKotlin: String by extra

  repositories {
    google()
    jcenter()
  }

  dependencies {
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$versionKotlin")
  }
}

repositories {
  google()
  jcenter()
}


plugins {
  val versionKotlin: String by extra
  id("java-gradle-plugin")
  kotlin("jvm") version versionKotlin
  `kotlin-dsl`
}

gradlePlugin {
  plugins {
    create("buildPluginOptions") {
      id = "com.github.hadilq.build-plugin"
      implementationClass = "com.github.hadilq.build.plugin.BuildPlugin"
    }
  }
}

val versionKotlin: String by extra
val versionAndroidBuildTools: String by extra
dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib:$versionKotlin")
  implementation("org.jetbrains.kotlin:kotlin-gradle-plugin-api:$versionKotlin")
  implementation("org.jetbrains.kotlin:kotlin-android-extensions:$versionKotlin")
  compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:$versionKotlin")
  compileOnly("com.android.tools.build:gradle:$versionAndroidBuildTools")
}
