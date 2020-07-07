package com.github.hadilq.youtubeapp.core.di

import android.content.Context
import coil.ImageLoader
import com.github.hadilq.youtubeapp.core.navigation.Navigator

interface CoreModule {

  fun navigator(context: Context): Navigator

  val imageLoader: ImageLoader
}
