package com.github.hadilq.youtubeapp.domain.entity

sealed class Error

sealed class TokenExpiration : Error() {
  abstract val throwable: Throwable
}

data class UserRecoverableAuthIOError(override val throwable: Throwable, val intent: Intent) : TokenExpiration()
data class GooglePlayServicesAvailabilityError(
  override val throwable: Throwable,
  val intent: Intent
) : TokenExpiration()

data class GoogleAuthIOError(override val throwable: Throwable) : TokenExpiration()
