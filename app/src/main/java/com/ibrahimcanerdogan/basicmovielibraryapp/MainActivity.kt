package com.ibrahimcanerdogan.basicmovielibraryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ibrahimcanerdogan.basicmovielibraryapp.ui.navigation.MovieNavigation
import com.ibrahimcanerdogan.basicmovielibraryapp.ui.theme.BasicMovieLibraryAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MoviePage {
                MovieNavigation()
            }
        }
    }
}

@Composable
fun MoviePage(mainContent: @Composable () -> Unit) {
    BasicMovieLibraryAppTheme {
        mainContent()
    }
}


@Preview(showBackground = true)
@Composable
fun MoviePagePreview() {
    MoviePage {
        MovieNavigation()
    }
}