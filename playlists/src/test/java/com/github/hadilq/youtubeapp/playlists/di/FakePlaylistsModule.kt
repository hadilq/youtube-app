package com.github.hadilq.youtubeapp.playlists.di

import android.content.Context
import coil.ImageLoader
import com.github.hadilq.youtubeapp.core.navigation.Navigator
import com.github.hadilq.youtubeapp.domain.repository.DeviceRepository
import com.github.hadilq.youtubeapp.domain.repository.GooglePlayRepository
import com.github.hadilq.youtubeapp.domain.repository.YoutubeRepository
import com.github.hadilq.youtubeapp.domain.usecase.GetPlaylistItems
import com.github.hadilq.youtubeapp.domain.usecase.GetPlaylists
import com.github.hadilq.youtubeapp.domain.usecase.GetSelectedAccountName
import com.github.hadilq.youtubeapp.domain.usecase.HandleErrors
import com.github.hadilq.youtubeapp.domain.usecase.IsDeviceOnline
import com.github.hadilq.youtubeapp.domain.usecase.IsGooglePlayServicesAvailable
import com.github.hadilq.youtubeapp.domain.usecase.IsGoogleUserResolvableError
import com.github.hadilq.youtubeapp.domain.usecase.LoadChannels
import com.github.hadilq.youtubeapp.domain.usecase.NewChooseAccountIntent
import com.github.hadilq.youtubeapp.domain.usecase.SetSelectedAccountName
import com.github.hadilq.youtubeapp.playlists.PlaylistViewHolderFactory
import com.github.hadilq.youtubeapp.playlists.PlaylistsAdapter
import com.github.hadilq.youtubeapp.playlists.PlaylistsViewModelFactory
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope

class FakePlaylistsModule(private val scope: CoroutineScope) : PlaylistsModule {
  override val playlistsViewModelFactory: PlaylistsViewModelFactory = mockk()

  override val playlistViewHolderFactory: PlaylistViewHolderFactory = mockk()

  override val playlistsAdapter: PlaylistsAdapter = mockk()

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

  override fun navigator(context: Context): Navigator = mockk()

  override val imageLoader: ImageLoader = mockk()

  override val viewModelScope: CoroutineScope = scope
}
