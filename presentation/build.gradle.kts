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
  kotlin("multiplatform")
  id("com.github.hadilq.build-plugin")
}

kotlin {
  jvm {
    compilations.all {
      kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
      }
    }
  }
  metadata {
    compilations.all {
      kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
      }
    }
  }

  sourceSets {
    commonMain {
      dependencies {
        implementation(project(":domain"))
        implementation(kotlin(KOTLIN_STDLIB_COMMON))
        implementation(COROUTINES_COMMON)
        implementation(COROUTINES_LIFECYCLE_COMMON)
      }
    }

    commonTest {
      dependencies {
        implementation(kotlin(KOTLIN_TEST_COMMON))
        implementation(kotlin(KOTLIN_TEST_ANNOTATIONS_COMMON))
        implementation(MOCKK_COMMON)
      }
    }

    val jvmMain by getting {
      dependencies {
        implementation(project(":domain"))
        implementation(kotlin(KOTLIN_STDLIB))
        implementation(COROUTINES)
        implementation(COROUTINES_LIFECYCLE_JVM)
      }
    }

    val jvmTest by getting {
      dependencies {
        implementation(kotlin(KOTLIN_TEST_JVM))
        implementation(MOCKK)
        implementation(COROUTINES_TEST)
        implementation(JUPITER_API)
        runtimeOnly(JUPITER_ENGINE)
      }
    }
  }

  experimental {
    coroutines = org.jetbrains.kotlin.gradle.dsl.Coroutines.ENABLE
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}
