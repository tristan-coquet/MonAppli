//Films.kt
package com.example.monappli

//A quoi ressemble les box films
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage

@Composable
fun Films(
    viewModel: MainViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToSeries: () -> Unit,
    onNavigateToActeurs: () -> Unit,
    onNavigateToFilmDetail: (TmdbMovie) -> Unit
) {
    val movies by viewModel.movies.collectAsState()
    var showSearchDialog by remember { mutableStateOf(false) }
    var hasLoaded by remember { mutableStateOf(false) }
    var showFavOnly by remember { mutableStateOf(false) }

    Log.d("Films", "📺 Films composable - Nombre de films : ${movies.size}")

    LaunchedEffect(Unit) {
        if (!hasLoaded) {
            Log.d("Films", "🚀 Premier chargement")
            viewModel.getFilmsInitiaux()
            hasLoaded = true
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = Color(0xFF6200EE)
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Films") },
                    label = { Text("Films") },
                    selected = true,
                    onClick = { /* Déjà sur Films */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.PlayArrow, contentDescription = "Séries") },
                    label = { Text("Séries") },
                    selected = false,
                    onClick = onNavigateToSeries
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Acteurs") },
                    label = { Text("Acteurs") },
                    selected = false,
                    onClick = onNavigateToActeurs
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showSearchDialog = true },
                containerColor = Color(0xFF6200EE),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Search, contentDescription = "Rechercher")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Retour",
                        tint = Color(0xFF6200EE)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "🎬 Films",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.weight(1f))

                // Icône pour le commutateur général favoris
                IconButton(onClick = {
                    showFavOnly = !showFavOnly
                    viewModel.setShowFavOnly(showFavOnly)
                }) {
                    Icon(
                        imageVector = if (showFavOnly) Icons.Filled.Favorite
                        else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (showFavOnly)
                            "Afficher tous les films"
                        else
                            "Afficher seulement les favoris"
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(movies) { movie ->
                    MovieCard(
                        movie = movie,
                        onClick = { onNavigateToFilmDetail(movie) },
                        onToggleFav = { viewModel.toggleFilmFav(movie) }
                    )
                }
            }
        }

        if (showSearchDialog) {
            SearchDialog(
                onDismiss = { showSearchDialog = false },
                onSearch = { query ->
                    viewModel.searchMovies(query)
                    showSearchDialog = false
                }
            )
        }
    }
}

@Composable
fun SearchDialog(
    onDismiss: () -> Unit,
    onSearch: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🔍 Rechercher un film",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    label = { Text("Titre du film") },
                    placeholder = { Text("Ex: Avatar, Matrix...") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF6200EE),
                        focusedLabelColor = Color(0xFF6200EE)
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Annuler")
                    }

                    Button(
                        onClick = { onSearch(searchText) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6200EE)
                        ),
                        enabled = searchText.isNotBlank()
                    ) {
                        Text("Rechercher")
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCard(
    movie: TmdbMovie,
    onClick: () -> Unit,
    onToggleFav: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = movie.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "📅 ${movie.release_date}",
                    fontSize = 14.sp,
                    color = Color(0xFF666666)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "⭐", fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format("%.1f/10", movie.vote_average),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF6200EE),
                        modifier = Modifier.weight(1f)
                    )
                    // Icône favori
                    IconButton(onClick = { onToggleFav() }) {
                        Icon(
                            imageVector = if (movie.isFav) Icons.Filled.Favorite
                            else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (movie.isFav)
                                "Retirer des favoris" else "Ajouter aux favoris"
                        )
                    }
                }
            }
        }
    }
}