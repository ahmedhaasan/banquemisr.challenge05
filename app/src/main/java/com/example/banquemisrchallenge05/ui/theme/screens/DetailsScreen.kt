package com.example.banquemisrchallenge05.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.viewmodels.MoviesViewModel

@Composable
fun DetailsScreen(navController: NavController, movie_id: Int){

    Log.d("TAG", "DetailsScreen: $movie_id")
}


@Composable
fun MovieDetailsItem(movie: Movie) {
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
        Row() {
            Text(
                text = "Title:",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.width(8.dp))

            CustomText(
                textToUse = " Title: ${movie.title}",
                textColor = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(vertical = 4.dp)

            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        Row() {
            Text(
                text = "Release Date:",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.width(8.dp))
            CustomText(
                textToUse = " ${movie.release_date}",
                textColor = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(4.dp)

            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row() {
            Text(
                text = "Overview:",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.width(8.dp))
            CustomText(
                textToUse = " ${movie.overview}",
                textColor = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(4.dp)

            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row() {
            Text(
                text = "gener_ids :",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.width(8.dp))
            CustomText(
                textToUse = " ${movie.genre_ids}",
                textColor = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(4.dp)

            )
        }
    }
}