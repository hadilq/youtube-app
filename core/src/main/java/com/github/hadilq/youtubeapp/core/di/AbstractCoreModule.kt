package com.github.hadilq.youtubeapp.core.di

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.EmptyCoroutineContext

abstract class AbstractCoreModule : CoreModule {

  override val viewModelScope: CoroutineScope
    get() = CoroutineScope(EmptyCoroutineContext)
}
