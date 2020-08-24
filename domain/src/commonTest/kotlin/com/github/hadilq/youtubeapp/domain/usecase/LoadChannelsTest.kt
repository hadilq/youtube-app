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
import com.github.hadilq.youtubeapp.domain.entity.*
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class LoadChannelsTest {

  @Test
  fun `execute left`() = with(FakeDomainModule()) {
    val channel = Channel("", "")
    runBlocking {
      with(youtubeRepository) { coEvery { loadChannels() } returns flowOf(Left(listOf(channel))) }
      val usecase = LoadChannels()

      val result = usecase.run { execute() }

      with(youtubeRepository) { coVerify { loadChannels() } }
      val singleResult = result.single()
      assert(singleResult is Left) { "it is not Left" }
      assertEquals((singleResult as Left<List<Channel>, Error>).left, listOf(channel))
    }
  }

  @Test
  fun `execute right`() = with(FakeDomainModule()) {
    runBlocking {
      val error = UserRecoverableAuthIOError(RuntimeException(), Intent())
      with(youtubeRepository) {
        coEvery { loadChannels() } returns
            flowOf(Right<List<Channel>, Error>(error))
      }
      val usecase = LoadChannels()

      val result = usecase.run { execute() }

      with(youtubeRepository) { coVerify { loadChannels() } }
      val singleResult = result.single()
      assert(singleResult is Right) { "it is not Right" }
      assertEquals((singleResult as Right<List<Channel>, Error>).right, error)
    }
  }
}
