package com.github.hadilq.youtubeapp.data.di

import com.github.hadilq.youtubeapp.domain.di.DomainModule
import com.github.hadilq.youtubeapp.domain.repository.GooglePlayRepository
import com.github.hadilq.youtubeapp.domain.repository.YoutubeRepository
import com.github.hadilq.youtubeapp.domain.usecase.GetPlaylistItems
import com.github.hadilq.youtubeapp.domain.usecase.GetPlaylists
import com.github.hadilq.youtubeapp.domain.usecase.GetSelectedAccountName
import com.github.hadilq.youtubeapp.domain.usecase.IsDeviceOnline
import com.github.hadilq.youtubeapp.domain.usecase.IsGooglePlayServicesAvailable
import com.github.hadilq.youtubeapp.domain.usecase.IsGoogleUserResolvableError
import com.github.hadilq.youtubeapp.domain.usecase.NewChooseAccountIntent
import com.github.hadilq.youtubeapp.domain.usecase.SetSelectedAccountName
import io.mockk.mockk

class FakeDomainModule(
  private val dataModule: DataModule = FakeDataModule()
) : DomainModule, DataModule by dataModule {

  override val youtubeRepository: YoutubeRepository = mockk()

  override val googlePlayRepository: GooglePlayRepository = mockk()

  override val getPlaylists: GetPlaylists = mockk()

  override val getPlaylistItems: GetPlaylistItems = mockk()

  override val getSelectedAccountName: GetSelectedAccountName = mockk()

  override val isDeviceOnline: IsDeviceOnline = mockk()

  override val isGooglePlayServicesAvailable: IsGooglePlayServicesAvailable = mockk()

  override val isGoogleUserResolvableError: IsGoogleUserResolvableError = mockk()

  override val newChooseAccountIntent: NewChooseAccountIntent = mockk()

  override val setSelectedAccountName: SetSelectedAccountName = mockk()
}
