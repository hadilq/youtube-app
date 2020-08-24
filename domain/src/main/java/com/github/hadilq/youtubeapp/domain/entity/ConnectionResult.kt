package com.github.hadilq.youtubeapp.domain.entity

sealed class ConnectionResult {

  abstract val code: Int

  data class Success(override val code: Int) : ConnectionResult()
  data class Fail(override val code: Int) : ConnectionResult()
}
