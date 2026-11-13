package com.example.jeudedes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import coil3.compose.AsyncImage

@Composable
fun FilmDetailScreen(
    viewModel: MainViewModel,
    windowClass: WindowSizeClass,
    onPosterClick: (String, String) -> Unit
) {
    val filmDetail by viewModel.filmDetail.collectAsStateWithLifecycle()

    val film = filmDetail

    if (film == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        when (windowClass.windowWidthSizeClass) {
            WindowWidthSizeClass.COMPACT -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = film.poster,
                        contentDescription = film.title,
                        modifier = Modifier
                            .height(300.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onPosterClick(film.poster, film.title) },
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    FilmInfos(film)
                }
            }
            else -> {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = film.poster,
                        contentDescription = film.title,
                        modifier = Modifier
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onPosterClick(film.poster, film.title) },
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        FilmInfos(film)
                    }
                }
            }
        }
    }
}

@Composable
fun FilmInfos(film: FilmDetail) {
    Text(
        text = film.title,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = listOf(film.year, film.runtime).filter { it.isNotEmpty() }.joinToString(" • "),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(8.dp))

    if (film.imdbRating.isNotEmpty()) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Text(
                text = "⭐ ${film.imdbRating} / 10",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    DetailItem(label = "Genre", value = film.genre)
    DetailItem(label = "Réalisateur", value = film.director)
    DetailItem(label = "Acteurs", value = film.actors)
    DetailItem(label = "Pays", value = film.country)
    DetailItem(label = "Langue", value = film.language)
    DetailItem(label = "Classification", value = film.rated)
    DetailItem(label = "Sortie", value = film.released)

    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = "Synopsis",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = film.plot,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun DetailItem(label: String, value: String) {
    if (value.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
