package com.github.hadilq.youtubeapp.di

import com.github.hadilq.youtubeapp.presentation.di.PresentationModule
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.EmptyCoroutineContext

class PresentationModuleImpl : PresentationModule {

  override val viewModelScope: CoroutineScope
    get() = CoroutineScope(EmptyCoroutineContext)
}
