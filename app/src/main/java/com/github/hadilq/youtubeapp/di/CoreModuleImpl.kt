package com.github.hadilq.youtubeapp.di

import android.content.Context
import com.github.hadilq.youtubeapp.NavigatorImpl
import com.github.hadilq.youtubeapp.core.di.AbstractCoreModule
import com.github.hadilq.youtubeapp.core.navigation.Navigator

class CoreModuleImpl : AbstractCoreModule() {

  override fun navigator(context: Context): Navigator =
    NavigatorImpl(context)
}
