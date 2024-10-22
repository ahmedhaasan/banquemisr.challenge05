package com.example.banquemisrchallenge05

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

const val API_KEY = "96006574db716a7104aa2d14e5e22559"

const val NOW_PLAYING = "Now Playing"
const val POPULAR = "Popular"
const val UPCOMING = "Upcoming"


val transparentBrush = Brush.horizontalGradient(
    colors = listOf(Color(0xFFFFFFFF), Color.Transparent) // White to transparent
)