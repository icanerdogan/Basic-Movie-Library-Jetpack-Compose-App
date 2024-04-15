package com.ibrahimcanerdogan.basicmovielibraryapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ibrahimcanerdogan.basicmovielibraryapp.data.model.MovieItem
import com.ibrahimcanerdogan.basicmovielibraryapp.data.model.getDummyMovie
import com.ibrahimcanerdogan.basicmovielibraryapp.ui.component.MovieRectangleImage
import com.ibrahimcanerdogan.basicmovielibraryapp.ui.theme.Color2Beige

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(modifier: Modifier = Modifier,
                 navController: NavController,
                 movieId: String?
) {
    val context = LocalContext.current
    val detailedMovie = getDummyMovie().first { it.movieImdbID == movieId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = detailedMovie.movieTitle) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color2Beige,
                    titleContentColor = Color.Black
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            tint = Color.Black,
                            contentDescription = "Detail Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(context, "${detailedMovie.movieTitle} added to favorites.", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            tint = Color.Red,
                            contentDescription = "Favorite"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color2Beige)
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MainContent(movie = detailedMovie)
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier, movie: MovieItem) {
    MovieDetailData(movie, modifier)
    MovieDetailScrollableImage(movie, modifier)
}

@Composable
private fun MovieDetailData(
    movie: MovieItem,
    modifier: Modifier
) {
    Text(
        text = movie.movieTitle,
        style = MaterialTheme.typography.headlineLarge
    )
    Text(
        text = movie.movieYear,
        style = MaterialTheme.typography.titleLarge
    )
    Text(
        text = movie.movieDirector,
        style = MaterialTheme.typography.titleLarge
    )
    Text(
        text = movie.movieGenre,
        style = MaterialTheme.typography.titleMedium
    )
    Text(
        text = movie.movieImdbRating,
        style = MaterialTheme.typography.titleMedium
    )
    Text(
        text = movie.moviePlot,
        modifier = modifier.padding(10.dp),
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun MovieDetailScrollableImage(
    movie: MovieItem,
    modifier: Modifier
) {
    LazyRow {
        items(movie.movieImages) { image ->
            ElevatedCard(
                modifier = modifier
                    .wrapContentSize()
                    .width(LocalConfiguration.current.screenWidthDp.dp)
                    .padding(10.dp),
                elevation = CardDefaults.elevatedCardElevation(5.dp)
            ) {
                MovieRectangleImage(image)
            }
        }
    }
}

