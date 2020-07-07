package com.github.hadilq.youtubeapp.core.navigation

import android.content.Intent

sealed class Page

object Playlist : Page()

data class Login(val intent: Intent?) : Page()
