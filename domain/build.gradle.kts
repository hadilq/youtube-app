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
  kotlin("jvm")
}
val versionKotlin: String by project
val versionPaging: String by project
val versionJunit: String by project
val versionMockk: String by project
val versionKotlinCoroutines: String by project

tasks.withType<Test> {
  useJUnitPlatform()
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib:$versionKotlin")
  implementation("androidx.paging:paging-common:$versionPaging")

  testImplementation("org.junit.jupiter:junit-jupiter-api:$versionJunit")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$versionJunit")
  testImplementation("io.mockk:mockk:$versionMockk")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$versionKotlinCoroutines")

}
