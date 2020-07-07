package com.github.hadilq.youtubeapp.di

import android.content.Context
import coil.ImageLoader
import com.github.hadilq.youtubeapp.NavigatorImpl
import com.github.hadilq.youtubeapp.core.di.AbstractCoreModule
import com.github.hadilq.youtubeapp.core.navigation.Navigator

class CoreModuleImpl(
  private val appModule: AppModule
) : AbstractCoreModule() {

  override fun navigator(context: Context): Navigator =
    NavigatorImpl(context)

  override val imageLoader: ImageLoader
    get() = appModule.imageLoader
}
