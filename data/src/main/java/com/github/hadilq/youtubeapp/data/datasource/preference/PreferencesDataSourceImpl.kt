package com.github.hadilq.youtubeapp.data.datasource.preference

import com.github.hadilq.youtubeapp.data.di.DataModule

class PreferencesDataSourceImpl : PreferencesDataSource {

  override suspend fun DataModule.readString(key: String, default: String?): String? =
    sharedPreferences.getString(key, default)

  override suspend fun DataModule.writeString(key: String, value: String?) {
    sharedPreferences.edit().putString(key, value).apply()
  }

  override suspend fun DataModule.readBoolean(key: String, default: Boolean): Boolean =
    sharedPreferences.getBoolean(key, default)

  override suspend fun DataModule.writeBoolean(key: String, value: Boolean) {
    sharedPreferences.edit().putBoolean(key, value).apply()
  }

  override suspend fun DataModule.readFloat(key: String, default: Float): Float =
    sharedPreferences.getFloat(key, default)

  override suspend fun DataModule.writeFloat(key: String, value: Float) {
    sharedPreferences.edit().putFloat(key, value).apply()
  }

  override suspend fun DataModule.readInt(key: String, default: Int): Int =
    sharedPreferences.getInt(key, default)

  override suspend fun DataModule.writeInt(key: String, value: Int) {
    sharedPreferences.edit().putInt(key, value).apply()
  }

  override suspend fun DataModule.readLong(key: String, default: Long): Long =
    sharedPreferences.getLong(key, default)

  override suspend fun DataModule.writeLong(key: String, value: Long) {
    sharedPreferences.edit().putLong(key, value).apply()
  }
}
