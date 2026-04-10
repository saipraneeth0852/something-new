package com.statushub.india.presentation.status_maker

import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.layer.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.draw.drawWithCache
import kotlinx.coroutines.launch
import androidx.hilt.navigation.compose.hiltViewModel
import com.statushub.india.util.AdManager
import android.app.Activity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusMakerScreen(
    onBackClick: () -> Unit,
    viewModel: StatusMakerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        AdManager.loadInterstitial(context)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Status Maker", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (state.step > 1) viewModel.setStep(state.step - 1) else onBackClick()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Crossfade(targetState = state.step, label = "StepTransition") { step ->
                when (step) {
                    1 -> CategoryStep(onCategorySelected = { viewModel.selectCategory(it) })
                    2 -> TemplateStep(
                        selectedCategory = state.selectedCategory,
                        templates = state.templates,
                        selectedTemplate = state.selectedTemplate,
                        onTemplateSelected = { viewModel.selectTemplate(it) },
                        onNext = {
                            AdManager.showInterstitial(context as Activity) {
                                viewModel.setStep(3)
                            }
                        }
                    )
                    3 -> {
                        val context = LocalContext.current
                        val graphicsLayer = rememberGraphicsLayer()
                        val coroutineScope = rememberCoroutineScope()

                        PreviewStep(
                            quote = state.selectedQuote?.text ?: "",
                            templateId = state.selectedTemplate,
                            graphicsLayer = graphicsLayer,
                            onRegenerate = { viewModel.regenerate() },
                            onShare = {
                                coroutineScope.launch {
                                    val bitmap = graphicsLayer.toImageBitmap().asAndroidBitmap()
                                    com.example.new_app.util.ShareUtils.shareImage(context, bitmap, "Share Status")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryStep(onCategorySelected: (String) -> Unit) {
    val categories = listOf(
        "Bhakti" to "🕉️",
        "Motivational" to "💪",
        "Good Morning" to "☀️",
        "Good Night" to "🌙"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Choose Your Theme",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        categories.forEach { (name, emoji) ->
            CategoryItem(name, emoji, onClick = { onCategorySelected(name) })
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CategoryItem(name: String, emoji: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = emoji, fontSize = 42.sp)
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = name, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
        }
    }
}

@Composable
fun TemplateStep(
    selectedCategory: String,
    templates: List<TemplateItem>,
    selectedTemplate: Int,
    onTemplateSelected: (Int) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pick a Style",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Text(
            text = "$selectedCategory Category",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Template grid — 2 columns
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(templates) { template ->
                TemplateCard(
                    template = template,
                    isSelected = template.id == selectedTemplate,
                    onClick = { onTemplateSelected(template.id) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            enabled = selectedTemplate != 0
        ) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Generate Status ✨")
        }
    }
}

data class TemplateItem(
    val id: Int,
    val name: String,
    val gradientColors: List<Color>,
    val emoji: String
)

@Composable
fun TemplateCard(
    template: TemplateItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val gradient = Brush.verticalGradient(template.gradientColors)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.85f)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
        ) {
            // Selected overlay indicator
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.9f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = template.gradientColors.first(),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = template.emoji, fontSize = 32.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = template.name,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun PreviewStep(
    quote: String,
    templateId: Int,
    graphicsLayer: androidx.compose.ui.graphics.layer.GraphicsLayer,
    onRegenerate: () -> Unit,
    onShare: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // The Generated Image Composable
        StatusCard(
            text = quote,
            templateId = templateId,
            modifier = Modifier
                .aspectRatio(1f)
                .drawWithCache {
                    onDrawWithContent {
                        graphicsLayer.record {
                            this@onDrawWithContent.drawContent()
                        }
                        drawLayer(graphicsLayer)
                    }
                }
                .clip(RoundedCornerShape(32.dp))
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onRegenerate,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Regenerate")
            }

            Button(
                onClick = onShare,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF25D366), // WhatsApp Green
                    contentColor = Color.White
                )
            ) {
                Icon(Icons.Default.Share, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Share to WA")
            }
        }
    }
}

@Composable
fun StatusCard(text: String, templateId: Int, modifier: Modifier = Modifier) {
    // Templates: we'll define a few gradients
    val gradient = when (templateId) {
        1 -> Brush.verticalGradient(listOf(Color(0xFF8E2DE2), Color(0xFF4A00E0)))
        2 -> Brush.verticalGradient(listOf(Color(0xFFFDC830), Color(0xFFF37335)))
        3 -> Brush.verticalGradient(listOf(Color(0xFF00c6ff), Color(0xFF0072ff)))
        else -> Brush.verticalGradient(listOf(Color(0xFF1D976C), Color(0xFF93F9B9)))
    }

    Box(
        modifier = modifier.background(gradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "“",
                fontSize = 80.sp,
                color = Color.White.copy(alpha = 0.5f),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = text,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 40.sp,
                    textAlign = TextAlign.Center
                ),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "#StatusHubIndia",
                style = MaterialTheme.typography.labelMedium,
                color = Color.White.copy(alpha = 0.7f),
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
