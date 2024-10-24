package com.example.banquemisrchallenge05.ui.theme.screens

import android.util.Log
import android.webkit.WebSettings
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.banquemisrchallenge05.NOW_PLAYING
import com.example.banquemisrchallenge05.POPULAR
import com.example.banquemisrchallenge05.UPCOMING
import com.example.banquemisrchallenge05.model.apistates.MovieApiState
import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.viewmodels.MoviesViewModel

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Brush
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.banquemisrchallenge05.NetworkErrorContent
import com.example.banquemisrchallenge05.model.pagination.MovieType
import com.example.banquemisrchallenge05.network.NetworkObserver
import com.example.banquemisrchallenge05.ui.theme.navigation.Screens
import com.google.gson.Gson

@Composable
fun HomeScreen(
    navController: NavController,
    moviesViewModel: MoviesViewModel,
    networkObserver: NetworkObserver
) {
    val isConnected by networkObserver.isConnected.collectAsState()
    if (isConnected) {
        val movies = moviesViewModel.movies.collectAsLazyPagingItems()  // collect
        Log.d("HomeScreen", "movies: ${movies.itemCount}")
        Scaffold(
            containerColor = Color.White,
        ) { innerPadding ->
            HomeContent(
                movies = movies,
                moviesViewModel,
                navController,
                innerPadding
            )

        }
    } else {
        NetworkErrorContent()
    }
}

@Composable
fun HomeContent(
    movies: LazyPagingItems<Movie>,
    moviesViewModel: MoviesViewModel,
    navController: NavController,
    innerPadding: PaddingValues
) {
    // Gradient overlay
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.7f),
                        Color.Black.copy(alpha = 0.9f)
                    )
                )
            )
    )
    {
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()

        ) {
            MovieChips(moviesViewModel)
            Spacer(modifier = Modifier.height(30.dp))
            MovieList(movies, navController)
        }
    }


}


// Replace movieCategories list
val movieCategories = listOf(
    MovieType.NOW_PLAYING,
    MovieType.POPULAR,
    MovieType.UPCOMING
)

//
@Composable
fun MovieChips(moviesViewModel: MoviesViewModel) {

    var selectedType by remember { mutableStateOf(MovieType.NOW_PLAYING) }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp) // Adjust the padding for the text inside the box
    ) {
        Text(
            text = "Categories",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black // Optional: adjust text color for better visibility
        )
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {

        items(movieCategories) { category ->
            val movieType = when (category) {
                MovieType.NOW_PLAYING -> MovieType.NOW_PLAYING
                MovieType.POPULAR -> MovieType.POPULAR
                MovieType.UPCOMING -> MovieType.UPCOMING
            }
            MovieChip(
                category = category,
                selected = category == selectedType,
                onSelected = {
                    selectedType = movieType
                    moviesViewModel.updateMovieType(movieType)
                }
            )

        }
    }
}


@Composable
fun MovieChip(
    category: MovieType,
    selected: Boolean,
    onSelected: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )

    )
    val backgroundColor by animateColorAsState(
        targetValue = when {
            selected -> MaterialTheme.colorScheme.primaryContainer
            else -> MaterialTheme.colorScheme.surface
        },
        animationSpec = tween(durationMillis = 300)
    )

    val contentColor by animateColorAsState(
        targetValue = when {
            selected -> MaterialTheme.colorScheme.onPrimaryContainer
            else -> MaterialTheme.colorScheme.onSurface
        },
        animationSpec = tween(durationMillis = 300)
    )

// create Surface
    Surface(
        onClick = onSelected,
        modifier = Modifier
            .scale(scale)
            .height(48.dp),
        shape = RoundedCornerShape(24.dp),
        color = backgroundColor,
        tonalElevation = if (selected) 8.dp else 2.dp,
        shadowElevation = if (isPressed) 4.dp else 0.dp,
        interactionSource = interactionSource
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = category.icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(20.dp)

            )
            Text(
                text = category.title,
                color = contentColor,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            // Animated check icon
            AnimatedVisibility(
                visible = selected,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally()
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }

}

// this list need attintion from me again after using pager
@Composable
fun MovieList(
    movies: LazyPagingItems<Movie>,
    navController: NavController
) {
    LazyRow(
        contentPadding = PaddingValues(8.dp)
    ) {
        items(
            count = movies.itemCount,
        ) { index ->
            movies[index]?.let { movie ->
                MovieItem(movie = movie, navController)
            }
        }
        movies.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingScreen() }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingScreen() }

                }

                loadState.refresh is LoadState.Error -> {
                    val error = movies.loadState.refresh as LoadState.Error
                    item {
                        ErrorScreen(
                            message = error.error.localizedMessage ?: "Error",
                            onRetry = { retry() }
                        )
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = movies.loadState.append as LoadState.Error // Fix here
                    item {
                        ErrorScreen(
                            message = error.error.localizedMessage ?: "Error",
                            onRetry = { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, navController: NavController) {

    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .animateContentSize()
            .clickable {
                navController.navigate(Screens.DetailScreen.createRoute(movie.id)) {
                    // popUpTo(Screens.HomeScreen.route) { inclusive = true }

                }
            },
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

            Spacer(modifier = Modifier.height(12.dp))

// Enhanced Title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Title:",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 20.sp,
                        letterSpacing = 0.5.sp
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp,
                        letterSpacing = 0.25.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Release Date:",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 16.sp,
                        letterSpacing = 0.5.sp
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = (movie.release_date),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        letterSpacing = 0.25.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
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