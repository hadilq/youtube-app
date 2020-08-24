package com.github.hadilq.youtubeapp.presentation.di

import com.github.hadilq.youtubeapp.domain.di.DomainModule
import com.github.hadilq.youtubeapp.domain.repository.DeviceRepository
import com.github.hadilq.youtubeapp.domain.repository.GooglePlayRepository
import com.github.hadilq.youtubeapp.domain.repository.YoutubeRepository
import com.github.hadilq.youtubeapp.domain.usecase.*
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope

class FakeModule(private val scope: CoroutineScope) : PresentationModule, DomainModule {
  override val viewModelScope: CoroutineScope = scope
  override val youtubeRepository: YoutubeRepository = mockk()
  override val googlePlayRepository: GooglePlayRepository = mockk()
  override val deviceRepository: DeviceRepository = mockk()
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
