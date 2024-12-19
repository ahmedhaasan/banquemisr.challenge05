package com.example.banquemisrchallenge05.ui.theme.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.banquemisrchallenge05.NetworkErrorContent
import com.example.banquemisrchallenge05.model.apistates.MovieApiState
import com.example.banquemisrchallenge05.model.network.NetworkObserver
import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.ui.theme.navigation.Screens
import com.example.banquemisrchallenge05.viewmodels.MoviesViewModel

/**
 *      this si the Searcvh Screen
 */

@Composable
fun SearchScreen(
    controller: NavController,
    moviesViewModel: MoviesViewModel,
    networkObserver: NetworkObserver
) {
    val isNetworkAvailable by networkObserver.isConnected.collectAsState(initial = false)
    if (isNetworkAvailable) {
        SearchContent(controller, moviesViewModel)
    } else {
        NetworkErrorContent()
    }
}

@Composable
fun SearchContent(controller: NavController, moviesViewModel: MoviesViewModel) {
    Box(
        modifier = Modifier
            .background(gradientColors)
            .fillMaxSize()
    ) {
        SearchBar(controller, moviesViewModel)
    }
}

@Composable
fun SearchBar(controller: NavController, moviesViewModel: MoviesViewModel) {
    var searchQuery by remember { mutableStateOf("Inception") }

    // Launch a search when the query changes
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotBlank()) {
            moviesViewModel.searchMovies(searchQuery)
        }
    }

    // Collect movies state
    val movies by moviesViewModel.moviesSearch.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(horizontal = 5.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White),
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search") },
            placeholder = { Text("Type something...") },
            singleLine = true,
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Filled.Close, contentDescription = "Clear search")
                    }
                }
            }
        )

        // Handle different states
        when (movies) {
            MovieApiState.Loading -> {
                LoadingScreen()
            }
            is MovieApiState.Success -> {
                val movieList = (movies as MovieApiState.Success).movies
                if (movieList.isEmpty()) {
                    EmptyResultsScreen()
                } else {
                    SearchResultsList(movieList, controller)
                }
            }
            is MovieApiState.Failure -> {
                val errorMsg = (movies as MovieApiState.Failure).msg.message
                Log.e("SearchScreen", "Error: $errorMsg")
                ErrorScreen(errorMsg!!)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchResultsList(movies: List<Movie>, controller: NavController) {
    LazyColumn  (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 10.dp, end = 10.dp)
            .background(Color.Transparent),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(
            count = movies.size,
        ) { index ->
            movies[index].let { movie ->
                SearchMovieItem(movie = movie, navController = controller,modifier = Modifier.animateItemPlacement())
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}


@Composable
fun EmptyResultsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("No results found.", color = Color.Gray)
    }
}

@Composable
fun ErrorScreen(errorMessage: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("An error occurred: $errorMessage", color = Color.Red)
    }
}


/// enhanced Movie Item for Search
/// enhanced move item for search
@Composable
fun SearchMovieItem(
    movie: Movie,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable {
                navController.navigate(Screens.DetailScreen.createRoute(movie.id))
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp,
            hoveredElevation = 6.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                CustomImage(
                    url = "https://image.tmdb.org/t/p/w500/${movie.poster_path}",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )

                // Rating Badge
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                ) {
                    Text(
                        text = "★ ${movie.vote_average}/10",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = movie.release_date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                AnimatedVisibility(visible = expanded) {
                    Column {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = movie.overview,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        // Expand/Collapse button
        IconButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(
                imageVector = if (expanded)
                    Icons.Default.KeyboardArrowUp
                else
                    Icons.Default.KeyboardArrowDown,
                contentDescription = if (expanded) "Show less" else "Show more"
            )
        }
    }
}
