package com.github.hadilq.youtubeapp.data.datasource.preference

import com.github.hadilq.youtubeapp.data.di.DataModule

interface PreferencesDataSource {

  suspend fun DataModule.readString(key: String, default: String?): String?

  suspend fun DataModule.writeString(key: String, value: String?)

  suspend fun DataModule.readBoolean(key: String, default: Boolean): Boolean

  suspend fun DataModule.writeBoolean(key: String, value: Boolean)

  suspend fun DataModule.readFloat(key: String, default: Float): Float

  suspend fun DataModule.writeFloat(key: String, value: Float)

  suspend fun DataModule.readInt(key: String, default: Int): Int

  suspend fun DataModule.writeInt(key: String, value: Int)

  suspend fun DataModule.readLong(key: String, default: Long): Long

  suspend fun DataModule.writeLong(key: String, value: Long)
}
