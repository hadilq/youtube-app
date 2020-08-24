package com.github.hadilq.youtubeapp.data.di

import com.github.hadilq.youtubeapp.domain.di.DomainModule
import com.github.hadilq.youtubeapp.domain.repository.GooglePlayRepository
import com.github.hadilq.youtubeapp.domain.repository.YoutubeRepository
import com.github.hadilq.youtubeapp.domain.usecase.*
import io.mockk.mockk

class FakeDomainModule(
  private val dataModule: DataModule = FakeDataModule()
) : DomainModule, DataModule by dataModule {

  override val youtubeRepository: YoutubeRepository = mockk()

  override val googlePlayRepository: GooglePlayRepository = mockk()

  override val getPlaylists: GetPlaylists = mockk()

  override val getPlaylistItems: GetPlaylistItems = mockk()

  override val getSelectedAccountName: GetSelectedAccountName = mockk()

  override val handleErrors: HandleErrors = mockk()

  override val isDeviceOnline: IsDeviceOnline = mockk()

  override val isGooglePlayServicesAvailable: IsGooglePlayServicesAvailable = mockk()

  override val isGoogleUserResolvableError: IsGoogleUserResolvableError = mockk()

  override val loadChannels: LoadChannels = mockk()

  override val newChooseAccountIntent: NewChooseAccountIntent = mockk()

  override val setSelectedAccountName: SetSelectedAccountName = mockk()
}
