package com.github.hadilq.youtubeapp.data.di

import android.content.Context
import com.github.hadilq.youtubeapp.data.datasource.youtube.YoutubeDataSource
import com.github.hadilq.youtubeapp.data.database.AppDatabase
import com.github.hadilq.youtubeapp.data.database.dao.PlaylistDao
import com.github.hadilq.youtubeapp.data.database.dao.PlaylistItemDao
import com.github.hadilq.youtubeapp.data.database.dao.PlaylistItemPageTokenDao
import com.github.hadilq.youtubeapp.data.database.dao.PlaylistPageTokenDao
import com.github.hadilq.youtubeapp.data.paging.PlaylistItemRemoteMediator
import com.github.hadilq.youtubeapp.data.paging.PlaylistRemoteMediator
import com.github.hadilq.youtubeapp.data.util.ParcelableUtil
import com.github.hadilq.youtubeapp.domain.entity.Playlist
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.services.youtube.YouTube
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

class FakeDataModule : DataModule {

  override val applicationContext: Context = mockk()

  override val youtubeDataSource: YoutubeDataSource = mockk()

  override val youtubeRepositoryImpl: YoutubeRepository = mockk()

  override val googleAccountCredential: GoogleAccountCredential = mockk()

  override val parcelableUtil: ParcelableUtil = mockk()

  override val youtube: YouTube = mockk()

  override val db: AppDatabase = mockk()

  override val playlistDao: PlaylistDao = mockk()

  override val playlistPageTokenDao: PlaylistPageTokenDao = mockk()

  override val playlistItemDao: PlaylistItemDao = mockk()

  override val playlistItemPageTokenDao: PlaylistItemPageTokenDao = mockk()

  override fun playlistRemoteMediator(query: String?, pageSize: Int): PlaylistRemoteMediator = mockk()

  override fun playlistItemRemoteMediator(playlist: Playlist, pageSize: Int): PlaylistItemRemoteMediator = mockk()

  @Suppress("BlockingMethodInNonBlockingContext")
  override suspend fun <T> networkCall(block: suspend CoroutineScope.() -> T): T = runBlocking {
    block()
  }
}
