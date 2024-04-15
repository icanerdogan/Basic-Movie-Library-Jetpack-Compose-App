package com.ibrahimcanerdogan.basicmovielibraryapp.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ibrahimcanerdogan.basicmovielibraryapp.data.model.MovieItem
import com.ibrahimcanerdogan.basicmovielibraryapp.data.model.getDummyMovie
import com.ibrahimcanerdogan.basicmovielibraryapp.ui.component.ComingSoonBanner
import com.ibrahimcanerdogan.basicmovielibraryapp.ui.component.MovieCircleImage
import com.ibrahimcanerdogan.basicmovielibraryapp.ui.navigation.MovieScreens
import com.ibrahimcanerdogan.basicmovielibraryapp.ui.theme.Color2Beige
import com.ibrahimcanerdogan.basicmovielibraryapp.ui.theme.Color2Blue
import com.ibrahimcanerdogan.basicmovielibraryapp.ui.theme.Color2Green
import com.ibrahimcanerdogan.basicmovielibraryapp.ui.theme.Color2Pink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Movies") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color2Beige,
                    titleContentColor = Color.Black
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color2Beige)
                .padding(it)
        ) {
            MovieContent(
                navController = navController
            )
        }
    }
}

@Composable
fun MovieContent(
    navController: NavController,
    movieList: ArrayList<MovieItem> = getDummyMovie()
) {
    LazyColumn {
        items(movieList) {
            MovieRow(movie = it) { movie ->
                navController.navigate(route = MovieScreens.DetailScreen.name + "/$movie")
            }
        }
    }
}

@Composable
fun MovieRow(
    modifier: Modifier = Modifier,
    movie: MovieItem,
    onItemClick: (String) -> Unit = {}
) {

    Card(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth()
            .clickable {
                onItemClick(movie.movieImdbID)
            },
        colors = CardDefaults.cardColors(
            containerColor = Color2Blue,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(corner = CornerSize(20.dp)),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            MovieRowPosterImage(movie = movie)

            Column(
                modifier = modifier.padding(10.dp)
            ) {
                MovieRowMainData(movie)
                MovieRowExpendableData(movie = movie)
            }
        }
    }
}

@Composable
private fun MovieRowPosterImage(
    modifier: Modifier = Modifier,
    movie: MovieItem
) {
    Surface(
        modifier = modifier
            .padding(10.dp)
            .size(100.dp),
        shape = CircleShape,
        shadowElevation = 10.dp
    ) {
        MovieCircleImage(imageUrl = movie.movieImages.first())
        if (movie.movieComingSoon) {
            ComingSoonBanner(imageUrl = "https://cdn-icons-png.flaticon.com/128/8089/8089442.png")
        }
    }
}

@Composable
private fun MovieRowMainData(movie: MovieItem) {
    Text(
        text = movie.movieTitle,
        style = MaterialTheme.typography.titleMedium
    )
    Text(
        text = "Director: ${movie.movieDirector}",
        style = MaterialTheme.typography.labelMedium
    )
    Text(
        text = "Date: ${movie.movieYear}",
        style = MaterialTheme.typography.labelMedium
    )
}

@Composable
private fun MovieRowExpendableData(modifier: Modifier = Modifier, movie: MovieItem) {
    var expandableState by remember { mutableStateOf(false) }

    AnimatedVisibility(visible = expandableState) {
        Column {
            Divider(
                modifier = modifier
                    .padding(vertical = 5.dp)
                    .border(0.4.dp, Color.Black)
            )
            Text(
                text = "${movie.moviePlot}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = modifier.padding(vertical = 3.dp))
            Text(
                text = "Actors: ${movie.movieActors}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = modifier.padding(vertical = 4.dp))
            Text(
                text = "Rating: ${movie.movieImdbRating}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

    Surface(
        modifier = modifier
            .clip(CircleShape)
            .padding(top = 10.dp)
            .border(1.dp, Color2Pink, CircleShape),
        color = Color2Green,
        shape = CircleShape,
    ) {
        Icon(
            imageVector = if (expandableState) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            modifier = modifier
                .size(25.dp)
                .clickable {
                    expandableState = !expandableState
                },
            tint = Color2Beige,
            contentDescription = "Down Arrow"
        )
    }
}