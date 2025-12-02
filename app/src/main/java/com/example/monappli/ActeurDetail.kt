package com.example.monappli
//Fichier pour Definir comment ressemblerons les détails box Acteurs
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActeurDetail(
    acteur: TmdbActeur,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détail de l'acteur") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Photo de l'acteur
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                if (!acteur.profile_path.isNullOrEmpty()) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/original${acteur.profile_path}",
                        contentDescription = acteur.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("👤", fontSize = 80.sp)
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = acteur.name,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Popularité
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🔥", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Popularité : ${String.format("%.1f", acteur.popularity)}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6200EE)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Métier
                Text(
                    text = "🎬 Métier",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = acteur.known_for_department ?: "Acting",
                    fontSize = 18.sp,
                    color = Color(0xFF333333)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Connu pour
                if (!acteur.known_for.isNullOrEmpty()) {
                    Text(
                        text = "⭐ Connu pour",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    acteur.known_for.take(5).forEach { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF5F5F5)
                            ),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Image
                                if (!item.poster_path.isNullOrEmpty()) {
                                    AsyncImage(
                                        model = "https://image.tmdb.org/t/p/w200${item.poster_path}",
                                        contentDescription = item.title ?: item.name,
                                        modifier = Modifier
                                            .width(60.dp)
                                            .height(90.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .width(60.dp)
                                            .height(90.dp)
                                            .background(Color.LightGray),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("🎬", fontSize = 24.sp)
                                    }
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    // Titre (film) ou nom (série)
                                    val displayTitle = when {
                                        !item.title.isNullOrBlank() -> item.title
                                        !item.name.isNullOrBlank() -> item.name
                                        else -> "Titre inconnu"
                                    }

                                    Text(
                                        text = displayTitle,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    // Date (film ou série)
                                    val displayDate = when {
                                        !item.release_date.isNullOrBlank() -> item.release_date
                                        !item.first_air_date.isNullOrBlank() -> item.first_air_date
                                        else -> "Date inconnue"
                                    }

                                    Text(
                                        text = "📅 $displayDate",
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    // Note
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "⭐", fontSize = 12.sp)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = String.format("%.1f/10", item.vote_average ?: 0.0),
                                            fontSize = 14.sp,
                                            color = Color(0xFF6200EE)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Informations supplémentaires
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF5F5F5)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        InfoRow("👤 ID TMDB", acteur.id.toString())
                        InfoRow("🎭 Département", acteur.known_for_department ?: "N/A")
                        InfoRow("🎬 Nombre de films connus", acteur.known_for.size.toString())
                        InfoRow("📊 Score de popularité", String.format("%.2f", acteur.popularity))
                    }
                }
            }
        }
    }
}

// ❌ SUPPRIME InfoRow d'ici, elle existe déjà dans MovieDetail.kt