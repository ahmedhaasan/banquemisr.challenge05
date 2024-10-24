package com.example.banquemisrchallenge05.model.pagination

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector

enum class MovieType {
    NOW_PLAYING,
    POPULAR,
    UPCOMING;
    val title: String
        get() = when (this) {
            NOW_PLAYING -> "Now Playing"
            POPULAR -> "Popular"
            UPCOMING -> "Upcoming"
        }

    val icon: ImageVector
        get() = when (this) {
            NOW_PLAYING -> Icons.Filled.PlayArrow
            POPULAR -> Icons.Filled.Favorite
            UPCOMING -> Icons.Filled.DateRange
        }
}
