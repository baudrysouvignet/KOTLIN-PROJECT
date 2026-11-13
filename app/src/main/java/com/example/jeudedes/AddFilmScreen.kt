package com.example.jeudedes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

@Composable
fun AddFilmScreen(
    viewModel: MainViewModel,
    windowClass: WindowSizeClass,
    onFilmAdded: () -> Unit
) {
    var titre by remember { mutableStateOf("") }
    var annee by remember { mutableStateOf("") }
    var realisateur by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var synopsis by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Ajouter un nouveau film",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        when (windowClass.windowWidthSizeClass) {
            WindowWidthSizeClass.COMPACT -> {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = titre,
                        onValueChange = { titre = it },
                        label = { Text("Titre du film") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = annee,
                        onValueChange = { annee = it },
                        label = { Text("Année de sortie") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = realisateur,
                        onValueChange = { realisateur = it },
                        label = { Text("Réalisateur") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = genre,
                        onValueChange = { genre = it },
                        label = { Text("Genre") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = synopsis,
                        onValueChange = { synopsis = it },
                        label = { Text("Synopsis") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 5
                    )
                }
            }
            else -> {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = titre,
                            onValueChange = { titre = it },
                            label = { Text("Titre du film") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = annee,
                            onValueChange = { annee = it },
                            label = { Text("Année de sortie") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = realisateur,
                            onValueChange = { realisateur = it },
                            label = { Text("Réalisateur") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = genre,
                            onValueChange = { genre = it },
                            label = { Text("Genre") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = synopsis,
                            onValueChange = { synopsis = it },
                            label = { Text("Synopsis") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 5,
                            maxLines = 8
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.addFilm(titre, annee, realisateur, genre, synopsis)
                onFilmAdded()
            },
            enabled = titre.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ajouter le film")
        }
    }
}
