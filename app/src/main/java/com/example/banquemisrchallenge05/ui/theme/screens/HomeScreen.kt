package com.example.banquemisrchallenge05.ui.theme.screens

import android.util.Log
import android.webkit.WebSettings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.banquemisrchallenge05.model.apistates.MovieApiState
import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.viewmodels.MoviesViewModel

@Composable
fun HomeScreen(navController: NavController, moviesViewModel: MoviesViewModel) {

    Scaffold(
        containerColor = Color.White,
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            fetchMovies(moviesViewModel)

        }

    }
}

@Composable
fun fetchMovies(moviesViewModel: MoviesViewModel) {
    LaunchedEffect(Unit) {
        moviesViewModel.getNowPlayingMovies(1)
    }
    val moviesStateFlow by moviesViewModel.nowPlayingMovies.collectAsState()
    when (moviesStateFlow) {
        is MovieApiState.Loading -> {
            // Show loading
        }

        is MovieApiState.Failure -> {
            Log.d("TAG", "fetchMovies: ${(moviesStateFlow as MovieApiState.Failure).msg}")
        }

        is MovieApiState.Success -> {
            // first
            val movies = (moviesStateFlow as MovieApiState.Success).movies
            Log.d("TAG", "fetchMovies: $movies")
        }
    }
}


@Composable
fun MovieItem(movie: Movie) {

}

@Composable
fun CustomImage(
    url: String,
) {
    val painter = rememberAsyncImagePainter(model = url)
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(128.dp)
            .padding(8.dp),
        contentScale = ContentScale.Fit,

        )
}

@Composable
fun CustomText(
    textToUse: String,
    textColor: Color,
    fontSize: TextUnit = 20.sp,
    fontWeight: FontWeight,
    modifier: Modifier = Modifier
) {
    Text(
        text = textToUse,
        color = textColor,
        fontSize = fontSize,
        fontWeight = fontWeight,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis
    )
}
