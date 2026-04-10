package com.statushub.india.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.statushub.india.presentation.components.AdMobBanner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToWallpapers: () -> Unit,
    onNavigateToQuotes: () -> Unit,
    onNavigateToStatusMaker: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("StatusHub India", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f)
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { /* Stay here */ },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.secondary,
                        selectedTextColor = MaterialTheme.colorScheme.secondary,
                        indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToWallpapers,
                    icon = { Text("🌅") }, // Using emoji as placeholder since image vector may be missing
                    label = { Text("Wallpapers") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToQuotes,
                    icon = { Text("💬") }, 
                    label = { Text("Quotes") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToStatusMaker,
                    icon = { Icon(Icons.Default.Star, contentDescription = "Maker") },
                    label = { Text("Maker") }
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            item {
                StatusMakerCard(onClick = onNavigateToStatusMaker)
            }

            item {
                RetentionStrip()
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        CategoryCard(
                            title = "Wallpapers",
                            description = "HD backgrounds",
                            icon = "🌅",
                            onClick = onNavigateToWallpapers
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        CategoryCard(
                            title = "Quotes",
                            description = "Perfect words",
                            icon = "💬",
                            onClick = onNavigateToQuotes
                        )
                    }
                }
            }

            item {
                AdMobBannerPlaceholder()
            }
            
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun StatusMakerCard(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFffb95f), // secondary_fixed_dim manually
                        Color(0xFFee9800)  // secondary_container manually
                    )
                )
            )
            .clickable { onClick() }
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Surface(
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "PREMIUM TOOL",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Status Maker ⭐",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = "Make Your Own Status",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Start Creating")
            }
        }
    }
}

@Composable
fun RetentionStrip() {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("☀️") 
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Good Morning!", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Your daily status is ready", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Button(onClick = { /* TODO share */ }) {
                Text("Share")
            }
        }
    }
}

@Composable
fun CategoryCard(
    title: String,
    description: String,
    icon: String,
    onClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = icon, fontSize = 32.sp)
            Column {
                Text(title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
fun AdMobBannerPlaceholder() {
    AdMobBanner(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}
