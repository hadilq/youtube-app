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
package com.github.hadilq.youtubeapp.domain.usecase

import com.github.hadilq.youtubeapp.domain.di.FakeDomainModule
import io.mockk.coEvery
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class NewChooseAccountIntentTest {

  @Test
  fun execute() = with(FakeDomainModule()) {
    val intent = byteArrayOf()
    runBlocking {
      with(youtubeRepository) { coEvery { newChooseAccountIntent() } returns intent }
      val usecase = NewChooseAccountIntent()

      val result = usecase.run { execute() }

      assertEquals(result, intent)
    }
  }
}
