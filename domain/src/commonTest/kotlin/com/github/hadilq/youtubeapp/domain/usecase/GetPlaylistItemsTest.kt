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
@file:Suppress("EXPERIMENTAL_UNSIGNED_LITERALS")

package com.github.hadilq.youtubeapp.domain.usecase

import com.github.hadilq.youtubeapp.domain.di.FakeDomainModule
import com.github.hadilq.youtubeapp.domain.entity.PagingData
import com.github.hadilq.youtubeapp.domain.entity.Playlist
import com.github.hadilq.youtubeapp.domain.entity.PlaylistItem
import com.github.hadilq.youtubeapp.domain.entity.Thumbnail
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GetPlaylistItemsTest {

  @Test
  fun execute() = with(FakeDomainModule()) {
    val playlist = Playlist("", 0L, "", Thumbnail("", 0u, 0u), "", 0u)
    val pagingData: PagingData<PlaylistItem> = mockk()
    runBlocking {
      with(youtubeRepository) {
        coEvery { startLoadingPlaylistItem(playlist) } returns flowOf(pagingData)
      }
      val usecase = GetPlaylistItems()

      val result = usecase.run { execute(playlist) }


      assertEquals(result.single(), pagingData)
    }
  }
}
