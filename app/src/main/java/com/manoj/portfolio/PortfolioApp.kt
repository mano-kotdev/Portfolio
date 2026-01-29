package com.manoj.portfolio

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class Skill(
    val name: String, val level: Float, val icon: ImageVector, val color: Color
)

data class Project(
    val title: String, val description: String, val tech: List<String>, val icon: ImageVector
)

sealed class Screen(val route: String, val icon: ImageVector, val title: String) {
    object About : Screen("about", Icons.Default.Person, "About Me")
    object Skills : Screen("skills", Icons.Default.Stars, "Skills")
    object Projects : Screen("projects", Icons.Default.Work, "Projects")
}

@Composable
fun PortfolioApp() {
    var selectedScreen by remember { mutableStateOf<Screen>(Screen.About) }
    val screens = listOf(Screen.About, Screen.Skills, Screen.Projects)

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFF6C63FF),
            secondary = Color(0xFFFF6584),
            tertiary = Color(0xFF4ECDC4),
            background = Color(0xFF0F0E17),
            surface = Color(0xFF1A1A2E)
        )
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                BottomNavigationBar(
                    screens = screens,
                    selectedScreen = selectedScreen,
                    onScreenSelected = { selectedScreen = it }
                )
            }
        ) { padding ->
            /*Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                AnimatedHeader()
                PrimaryTabRow(
                    selectedTabIndex = selectedScreen,
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.primary,
                    indicator = {
                        TabRowDefaults.PrimaryIndicator(
                            Modifier.tabIndicatorOffset(selectedScreen, matchContentSize = true),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                ) {
                    screens.forEachIndexed { index, title ->
                        Tab(
                            selected = index == selectedScreen,
                            onClick = { selectedScreen = index },
                            text = {
                                Text(
                                    title,
                                    fontWeight = if (selectedScreen == index) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        )
                    }
                }
                AnimatedContent(
                    targetState = selectedScreen,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(300)).togetherWith(
                            fadeOut(
                                animationSpec = tween(
                                    300
                                )
                            )
                        )
                    },
                    label = "content"
                ) { tab ->
                    when (tab) {
                        0 -> AboutSection()
                        1 -> SkillsSection()
                        2 -> ProjectsSection()
                    }
                }
            }*/
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                AnimatedContent(
                    targetState = selectedScreen,
                    transitionSpec = {
                        (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                            slideOutHorizontally { width -> width } + fadeOut()
                        )
                    },
                    label = "content"
                ) { screen ->
                    when (screen) {
                        Screen.About -> AboutSection()
                        Screen.Skills -> SkillsSection()
                        Screen.Projects -> ProjectsSection()
                    }
                }
            }
        }
    }
}


@Composable
fun AnimatedHeader() {
    var visible by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        visible = true
    }
    AnimatedVisibility(
        visible = visible, enter = slideInVertically(
            initialOffsetY = { -it }, animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
            )
        ) + fadeIn()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF6C63FF), Color(0xFF4ECDC4)
                        )
                    )
                )
                .padding(32.dp), contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PulsingProfileCircle()
                Text(
                    "Manoj Kumar R",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    "Senior Android Developer",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

@Composable
fun PulsingProfileCircle() {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.1f, animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing), repeatMode = RepeatMode.Reverse
        ), label = "scale"
    )
    Box(
        modifier = Modifier
            .size(120.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    listOf(
                        Color(0xFFFF6584), Color(0xFF6C63FF)
                    )
                )
            ), contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Default.Person2,
            contentDescription = "Profile",
            modifier = Modifier.size(64.dp),
            tint = Color.White
        )
    }
}

@Composable
fun BottomNavigationBar(
    screens: List<Screen>,
    selectedScreen: Screen,
    onScreenSelected: (Screen) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(screen.title, style = MaterialTheme.typography.labelSmall)
                },
                selected = selectedScreen == screen,
                onClick = {
                    onScreenSelected(screen)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                )
            )
        }
    }
}
