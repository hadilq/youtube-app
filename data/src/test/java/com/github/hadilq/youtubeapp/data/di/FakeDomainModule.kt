package com.github.hadilq.youtubeapp.data.di

import com.github.hadilq.youtubeapp.domain.di.DomainModule
import com.github.hadilq.youtubeapp.domain.navigation.Navigator
import com.github.hadilq.youtubeapp.domain.repository.YoutubeRepository
import io.mockk.mockk

class FakeDomainModule(
  private val dataModule: DataModule = FakeDataModule()
) : DomainModule, DataModule by dataModule {

  override val youtubeRepository: YoutubeRepository = mockk()

  override val navigator: Navigator = mockk()
}
