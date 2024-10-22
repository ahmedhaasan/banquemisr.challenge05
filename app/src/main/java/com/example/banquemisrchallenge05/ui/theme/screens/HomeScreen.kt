package com.example.banquemisrchallenge05.ui.theme.screens

import android.util.Log
import android.webkit.WebSettings
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.banquemisrchallenge05.model.apistates.MovieApiState
import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.viewmodels.MoviesViewModel

@Composable
fun HomeScreen(navController: NavController, moviesViewModel: MoviesViewModel) {

    Scaffold(
        containerColor = Color.White,
    ) { innerPadding ->
        HomeContent(moviesViewModel, innerPadding)

    }
}

@Composable
fun HomeContent(moviesViewModel: MoviesViewModel, innerPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()

    ) {

        fetchMovies(moviesViewModel)
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
            LoadingScreen() // when loading
        }

        is MovieApiState.Failure -> {
            val state = moviesStateFlow as MovieApiState.Failure
            ErrorScreen(message = state.msg.toString()) {
                moviesViewModel.getNowPlayingMovies(1)
            }
        }

        is MovieApiState.Success -> {
            // first
            val movies = (moviesStateFlow as MovieApiState.Success).movies
            Log.d("TAG", "fetchMovies: $movies")
            MovieList(movies)
        }
    }
}

@Composable
fun MovieList(movies: List<Movie>) {
    LazyRow(
        contentPadding = PaddingValues(8.dp)
    ) {
        itemsIndexed(movies) { index, movie ->
            MovieItem(movie)

        }
    }
}

@Composable
fun MovieItem(movie: Movie) {

    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .animateContentSize()
            .clickable {},
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp,
            hoveredElevation = 6.dp
        ),
        shape = RoundedCornerShape(20.dp)

    ) {
        Column(
            modifier = Modifier.padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            CustomImage(
                url = "https://image.tmdb.org/t/p/w500/${movie.poster_path}",
                modifier = Modifier
                    .height(450.dp)
                    .size(300.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .border(2.dp, Color.Gray, RoundedCornerShape(20.dp))
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            CustomText(
                textToUse = " Title: ${movie.title}",
                textColor = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(vertical = 4.dp)

            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomText(
                textToUse = "Release Date: ${movie.release_date}",
                textColor = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(4.dp)

            )
        }
    }

}

@Composable
fun CustomImage(
    url: String,
    modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(model = url)
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,

        )
}

@Composable
fun CustomText(
    textToUse: String,
    textColor: Color,
    fontSize: TextUnit = 20.sp,
    fontWeight: FontWeight,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium

) {
    Text(
        text = textToUse,
        color = textColor,
        fontSize = fontSize,
        fontWeight = fontWeight,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        style = style
    )
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}


@Composable
fun ErrorScreen(message: String, onRetry: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomText(
            textToUse = "Oops! Something went wrong",
            textColor = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.wrapContentSize(),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomText(
            textToUse = message,
            textColor = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.wrapContentSize()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }

    }
}