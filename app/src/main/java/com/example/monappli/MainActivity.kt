package com.example.monappli
//Page 1 et 2
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.monappli.ui.theme.MonAppliTheme

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MonAppliTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {

                        val backStack = remember {
                            mutableStateListOf<Any>(ProfileDestination())
                        }

                        val viewModel: MainViewModel = viewModel()

                        BackHandler(enabled = backStack.size > 1) {
                            backStack.removeLastOrNull()
                        }

                        NavDisplay(
                            backStack = backStack,
                            entryProvider = entryProvider {
                                // Écran de profil
                                entry<ProfileDestination> {
                                    Profil(
                                        onNavigateToFilms = { backStack.add(FilmsDestination()) }
                                    )
                                }

                                // Écran Films
                                entry<FilmsDestination> {
                                    Films(
                                        viewModel = viewModel,
                                        onNavigateBack = { backStack.removeLastOrNull() },
                                        onNavigateToSeries = { backStack.add(SeriesDestination()) },
                                        onNavigateToActeurs = { backStack.add(ActeursDestination()) },
                                        onNavigateToFilmDetail = { movie ->
                                            backStack.add(FilmDetailDestination(movie.id))
                                        }
                                    )
                                }

                                // Écran Séries
                                entry<SeriesDestination> {
                                    Series(
                                        viewModel = viewModel,
                                        onNavigateBack = { backStack.removeLastOrNull() },
                                        onNavigateToFilms = {
                                            backStack.removeLastOrNull()
                                        },
                                        onNavigateToActeurs = { backStack.add(ActeursDestination()) },
                                        onNavigateToSerieDetail = { serie ->
                                            backStack.add(SerieDetailDestination(serie.id))
                                        }
                                    )
                                }

                                // Écran Acteurs
                                entry<ActeursDestination> {
                                    Acteurs(
                                        viewModel = viewModel,
                                        onNavigateBack = { backStack.removeLastOrNull() },
                                        onNavigateToFilms = {
                                            backStack.removeLastOrNull()
                                        },
                                        onNavigateToSeries = {
                                            backStack.removeLastOrNull()
                                            backStack.add(SeriesDestination())
                                        },
                                        onNavigateToActeurDetail = { acteur ->
                                            backStack.add(ActeurDetailDestination(acteur.id))
                                        }
                                    )
                                }

                                // 🎬 Détail d'un film
                                entry<FilmDetailDestination> { backStackEntry ->
                                    val destination = backStackEntry as FilmDetailDestination
                                    val movieId = destination.filmId

                                    // Trouver le film dans la liste
                                    val movie = viewModel.movies.value.find { it.id == movieId }

                                    if (movie != null) {
                                        MovieDetail(
                                            movie = movie,
                                            onNavigateBack = { backStack.removeLastOrNull() }
                                        )
                                    }
                                }

                                // 📺 Détail d'une série
                                entry<SerieDetailDestination> { backStackEntry ->
                                    val destination = backStackEntry as SerieDetailDestination
                                    val serieId = destination.serieId

                                    // Trouver la série dans la liste
                                    val serie = viewModel.series.value.find { it.id == serieId }

                                    if (serie != null) {
                                        SerieDetail(
                                            serie = serie,
                                            onNavigateBack = { backStack.removeLastOrNull() }
                                        )
                                    }
                                }

                                // 🎭 Détail d'un acteur
                                entry<ActeurDetailDestination> { backStackEntry ->
                                    val destination = backStackEntry as ActeurDetailDestination
                                    val acteurId = destination.acteurId

                                    // Trouver l'acteur dans la liste
                                    val acteur = viewModel.acteurs.value.find { it.id == acteurId }

                                    if (acteur != null) {
                                        ActeurDetail(
                                            acteur = acteur,
                                            onNavigateBack = { backStack.removeLastOrNull() }
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}