package com.example.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapp.ui.BiodataScreen
import com.example.myapp.ui.DailyActivityScreen
import com.example.myapp.ui.EducationScreen
import com.example.myapp.ui.theme.MyAppTheme
import androidx.compose.ui.tooling.preview.Preview

sealed class Screen(val route: String, val label: Int, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Biodata : Screen("biodata", R.string.nav_biodata, Icons.Filled.AccountCircle)
    object Education : Screen("education", R.string.nav_education, Icons.Filled.Book)
    object Activity : Screen("activity", R.string.nav_activity, Icons.Filled.ListAlt)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                val navController = rememberAnimatedNavController()
                val screens = listOf(Screen.Biodata, Screen.Education, Screen.Activity)
                Scaffold(
                    topBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route
                        TopAppBar(
                            title = {
                                Text(
                                    text = when (currentRoute) {
                                        Screen.Biodata.route -> stringResource(R.string.biodata_title)
                                        Screen.Education.route -> stringResource(R.string.education_title)
                                        Screen.Activity.route -> stringResource(R.string.activity_title)
                                        else -> stringResource(R.string.app_name)
                                    }
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.White,
                                titleContentColor = Color.Black
                            )
                        )
                    },
                    bottomBar = {
                        BottomNavigationBar(navController, screens)
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Screen.Biodata.route,
                        modifier = Modifier.padding(innerPadding),
                        enterTransition = { fadeIn(animationSpec = tween(300)) + slideInHorizontally(initialOffsetX = { 300 }) },
                        exitTransition = { fadeOut(animationSpec = tween(300)) + slideOutHorizontally(targetOffsetX = { -300 }) },
                        popEnterTransition = { fadeIn(animationSpec = tween(300)) + slideInHorizontally(initialOffsetX = { -300 }) },
                        popExitTransition = { fadeOut(animationSpec = tween(300)) + slideOutHorizontally(targetOffsetX = { 300 }) }
                    ) {
                        composable(Screen.Biodata.route) { BiodataScreen() }
                        composable(Screen.Education.route) { EducationScreen() }
                        composable(Screen.Activity.route) { DailyActivityScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, screens: List<Screen>) {
    NavigationBar(
        containerColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        screens.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(screen.icon, contentDescription = stringResource(screen.label)) },
                label = { Text(stringResource(screen.label)) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Black.copy(alpha = 0.6f),
                    unselectedTextColor = Color.Black.copy(alpha = 0.6f),
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                )
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyAppTheme {
        Greeting("Android")
    }
}

data class ScreenMenu(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)