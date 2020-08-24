package com.github.hadilq.youtubeapp.presentation.di

import com.github.hadilq.youtubeapp.domain.di.DomainModule
import kotlinx.coroutines.CoroutineScope

interface PresentationModule {

  val viewModelScope: CoroutineScope
}

fun PresentationModule.fixDomain() = this as DomainModule

