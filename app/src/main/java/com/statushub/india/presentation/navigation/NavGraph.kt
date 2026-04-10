package com.statushub.india.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.statushub.india.presentation.home.HomeScreen
import com.statushub.india.presentation.quotes.QuotesScreen
import com.statushub.india.presentation.splash.SplashScreen
import com.statushub.india.presentation.status_maker.StatusMakerScreen
import com.statushub.india.presentation.wallpapers.WallpaperDetailScreen
import com.statushub.india.presentation.wallpapers.WallpapersScreen

object Destinations {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val WALLPAPERS = "wallpapers"
    const val QUOTES = "quotes"
    const val STATUS_MAKER = "status_maker"
    const val WALLPAPER_DETAIL = "wallpaper_detail/{url}"
}

@Composable
fun StatusHubNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.SPLASH
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destinations.SPLASH) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.SPLASH) { inclusive = true }
                    }
                }
            )
        }
        composable(Destinations.HOME) {
            HomeScreen(
                onNavigateToWallpapers = { navController.navigate(Destinations.WALLPAPERS) },
                onNavigateToQuotes = { navController.navigate(Destinations.QUOTES) },
                onNavigateToStatusMaker = { navController.navigate(Destinations.STATUS_MAKER) }
            )
        }
        composable(Destinations.WALLPAPERS) {
            WallpapersScreen(
                onBackClick = { navController.popBackStack() },
                onWallpaperClick = { url ->
                    val encodedUrl = java.net.URLEncoder.encode(url, "UTF-8")
                    navController.navigate("wallpaper_detail/$encodedUrl")
                }
            )
        }
        composable(
            route = Destinations.WALLPAPER_DETAIL,
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url")?.let {
                java.net.URLDecoder.decode(it, "UTF-8")
            } ?: ""
            WallpaperDetailScreen(
                imageUrl = url,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Destinations.QUOTES) {
            QuotesScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Destinations.STATUS_MAKER) {
            StatusMakerScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
