package com.github.hadilq.youtubeapp.domain.entity

sealed class Either<L, R>
data class Left<L, R>(val left: L) : Either<L, R>()
data class Right<L, R>(val right: R) : Either<L, R>()
