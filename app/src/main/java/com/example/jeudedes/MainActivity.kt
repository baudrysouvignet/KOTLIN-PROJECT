package com.example.jeudedes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.jeudedes.ui.theme.JeuDeDesTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewmodel: MainViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

            JeuDeDesTheme {
                val backStack = remember { mutableStateListOf<Any>(FilmListDestination()) }

                val allerRecherche: () -> Unit = {
                    backStack.clear()
                    backStack.add(FilmListDestination())
                }
                val allerAjout: () -> Unit = {
                    if (backStack.lastOrNull() !is AddFilmDestination) {
                        backStack.add(AddFilmDestination())
                    }
                }

                Row(modifier = Modifier.fillMaxSize()) {
                    // en paysage / grand écran : barre de navigation à gauche
                    if (windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT) {
                        NavigationRail {
                            NavigationRailItem(
                                selected = backStack.lastOrNull() is FilmListDestination,
                                onClick = allerRecherche,
                                icon = {
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = "Recherche"
                                    )
                                },
                                label = { Text("Recherche") }
                            )
                            NavigationRailItem(
                                selected = backStack.lastOrNull() is AddFilmDestination,
                                onClick = allerAjout,
                                icon = {
                                    Icon(
                                        Icons.Default.Add,
                                        contentDescription = "Ajouter"
                                    )
                                },
                                label = { Text("Ajouter") }
                            )
                        }
                    }

                    Scaffold(
                        modifier = Modifier.weight(1f),
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        when (backStack.lastOrNull()) {
                                            is FilmListDestination -> "Recherche de films"
                                            is FilmDetailDestination -> "Détails du film"
                                            is AddFilmDestination -> "Ajouter un film"
                                            is PosterFullScreenDestination -> "Poster"
                                            else -> "Films"
                                        }
                                    )
                                },
                                navigationIcon = {
                                    if (backStack.size > 1) {
                                        IconButton(onClick = { backStack.removeLastOrNull() }) {
                                            Icon(
                                                Icons.AutoMirrored.Filled.ArrowBack,
                                                contentDescription = "Retour"
                                            )
                                        }
                                    }
                                }
                            )
                        },

                        bottomBar = {
                            if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                                NavigationBar {
                                    NavigationBarItem(
                                        selected = backStack.lastOrNull() is FilmListDestination,
                                        onClick = allerRecherche,
                                        icon = {
                                            Icon(
                                                Icons.Default.Search,
                                                contentDescription = "Recherche"
                                            )
                                        },
                                        label = { Text("Recherche") }
                                    )
                                    NavigationBarItem(
                                        selected = backStack.lastOrNull() is AddFilmDestination,
                                        onClick = allerAjout,
                                        icon = {
                                            Icon(
                                                Icons.Default.Add,
                                                contentDescription = "Ajouter"
                                            )
                                        },
                                        label = { Text("Ajouter") }
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        NavDisplay(
                            modifier = Modifier.padding(innerPadding),
                            backStack = backStack,
                            entryProvider = entryProvider {
                                entry<FilmListDestination> {
                                    when (windowSizeClass.windowWidthSizeClass) {
                                        // écran assez grand : liste à gauche, détails à droite
                                        WindowWidthSizeClass.EXPANDED -> {
                                            FilmListDetailScreen(
                                                viewModel = viewmodel,
                                                windowClass = windowSizeClass,
                                                onPosterClick = { posterUrl, title ->
                                                    backStack.add(PosterFullScreenDestination(posterUrl, title))
                                                }
                                            )
                                        }
                                        else -> {
                                            FilmListScreen(
                                                viewModel = viewmodel,
                                                windowClass = windowSizeClass,
                                                onFilmClick = { imdbId ->
                                                    backStack.add(FilmDetailDestination(imdbId))
                                                }
                                            )
                                        }
                                    }
                                }
                                entry<FilmDetailDestination> {
                                    FilmDetailScreen(
                                        viewModel = viewmodel,
                                        windowClass = windowSizeClass,
                                        onPosterClick = { posterUrl, title ->
                                            backStack.add(PosterFullScreenDestination(posterUrl, title))
                                        }
                                    )
                                }
                                entry<PosterFullScreenDestination> { dest ->
                                    PosterFullScreen(posterUrl = dest.posterUrl, title = dest.title)
                                }
                                entry<AddFilmDestination> {
                                    AddFilmScreen(
                                        viewModel = viewmodel,
                                        windowClass = windowSizeClass,
                                        onFilmAdded = allerRecherche
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
