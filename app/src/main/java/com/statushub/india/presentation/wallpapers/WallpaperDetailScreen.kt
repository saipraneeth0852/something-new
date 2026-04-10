package com.statushub.india.presentation.wallpapers

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperDetailScreen(
    imageUrl: String,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = { Text("Preview", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Full Wallpaper",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )

            // Dynamic Action Bar at the Bottom
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp)
                    .fillMaxWidth(),
                color = Color.Black.copy(alpha = 0.6f),
                shape = RoundedCornerShape(24.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(Color.White.copy(alpha = 0.2f)))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WallpaperAction(
                        icon = Icons.Default.Download,
                        label = "Download",
                        onClick = { downloadImage(context, imageUrl) }
                    )
                    
                    VerticalDivider(modifier = Modifier.height(32.dp), color = Color.White.copy(alpha = 0.2f))
                    
                    WallpaperAction(
                        icon = Icons.Default.Share,
                        label = "Share",
                        onClick = { 
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "Check out this beautiful wallpaper from StatusHub India: $imageUrl")
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(sendIntent, "Share Wallpaper"))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun WallpaperAction(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(icon, contentDescription = label, tint = Color.White, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, color = Color.White, style = MaterialTheme.typography.labelSmall)
    }
}

private fun downloadImage(context: Context, url: String) {
    try {
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Wallpaper Download")
            .setDescription("Downloading high-quality wallpaper...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "StatusHub_${System.currentTimeMillis()}.jpg")
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
        Toast.makeText(context, "Download started...", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Failed to download", Toast.LENGTH_SHORT).show()
    }
}
