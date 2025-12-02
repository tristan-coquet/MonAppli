//Profil.kt
package com.example.monappli
//Page de Démarage de l'app
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowWidthSizeClass

@Composable
fun Profil(onNavigateToFilms: () -> Unit) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            CompactScreen(onNavigateToFilms)
        }
        else -> {
            ExpandedScreen(onNavigateToFilms)
        }
    }
}

@Composable
fun CompactScreen(onNavigateToFilms: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ProfileImage(size = 135.dp)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Tristan Coquet",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Pro player Chagpt / Claude AI",
                fontSize = 22.sp,
                color = Color(0xFF666666),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Prestigious Unemployment University / PUU",
                fontSize = 18.sp,
                color = Color(0xFF666666),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            ContactInfo()

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onNavigateToFilms,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6200EE)
                ),
                modifier = Modifier
                    .height(36.dp)
                    .widthIn(min = 100.dp)
            ) {
                Text(
                    text = "Démarrer",
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun ExpandedScreen(onNavigateToFilms: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ProfileImage(size = 140.dp)
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Tristan Coquet",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Pro player Chagpt / Claude AI",
                    fontSize = 18.sp,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Chômeur pro",
                    fontSize = 16.sp,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.width(24.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ContactInfo()
                Spacer(modifier = Modifier.height(35.dp))

                Button(
                    onClick = onNavigateToFilms,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6200EE)
                    ),
                    modifier = Modifier
                        .height(40.dp)
                        .widthIn(min = 160.dp)
                ) {
                    Text(
                        text = "Démarrer",
                        fontSize = 22.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileImage(size: androidx.compose.ui.unit.Dp) {
    Image(
        painter = painterResource(id = R.drawable.mi_head),
        contentDescription = "Photo de profil",
        modifier = Modifier
            .size(size)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ContactInfo() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.outline_alternate_email_24),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Color(0xFF666666)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "tristan.coquet.ciel@gmail.com",
                fontSize = 20.sp,
                color = Color(0xFF666666)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_work_24),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Color(0xFF666666)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "www.linkedin.com/in/tristan-coquet-ciel",
                fontSize = 20.sp,
                color = Color(0xFF666666)
            )
        }
    }
}