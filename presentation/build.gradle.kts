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
  kotlin("jvm")
  id("com.github.hadilq.build-plugin")
}

addJUnit()

dependencies {
  implementation(project(":domain"))

  implementation(kotlin(KOTLIN_STDLIB))
  implementation(PAGING_COMMON)
  implementation(COROUTINES_LIFECYCLE_JVM)

  testImplementation(MOCKK)
  testImplementation(COROUTINES_TEST)
}
