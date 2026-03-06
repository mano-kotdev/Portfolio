package com.manoj.portfolio

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun SkillsSection() {
    val skillCategories = remember {
        listOf(
            SkillCategory(
                title = "Language",
                skills = listOf(
                    Skill("Kotlin", 0.95f, Color(0xFF6C63FF)),
                    Skill("Java", 0.90f, Color(0xFF6C63FF)),
                    Skill("C/C++ (JNI/NDK)", 0.75f, Color(0xFF6C63FF))
                ),
                icon = Icons.Default.Code,
                accentColor = Color(0xFF6C63FF)
            ),
            SkillCategory(
                title = "Android",
                skills = listOf(
                    Skill("Android SDK", 0.95f, Color(0xFF4ECDC4)),
                    Skill("ViewModel", 0.95f, Color(0xFF4ECDC4)),
                    Skill("LiveData", 0.90f, Color(0xFF4ECDC4)),
                    Skill("Room", 0.90f, Color(0xFF4ECDC4)),
                    Skill("Navigation", 0.85f, Color(0xFF4ECDC4)),
                    Skill("WorkManager", 0.85f, Color(0xFF4ECDC4))
                ),
                icon = Icons.Default.Android,
                accentColor = Color(0xFF4ECDC4)
            ),
            SkillCategory(
                title = "Architecture",
                skills = listOf(
                    Skill("MVVM", 0.95f, Color(0xFFFF6584)),
                    Skill("Clean Architecture", 0.90f, Color(0xFFFF6584)),
                    Skill("DI (Dagger/Hilt)", 0.90f, Color(0xFFFF6584)),
                    Skill("Repository Pattern", 0.95f, Color(0xFFFF6584))
                ),
                icon = Icons.Default.Architecture,
                accentColor = Color(0xFFFF6584)
            ),
            SkillCategory(
                title = "Concurrency",
                skills = listOf(
                    Skill("Coroutines", 0.95f, Color(0xFFFFA500)),
                    Skill("Flow", 0.90f, Color(0xFFFFA500))
                ),
                icon = Icons.Default.Speed,
                accentColor = Color(0xFFFFA500)
            ),
            SkillCategory(
                title = "UI",
                skills = listOf(
                    Skill("XML", 0.90f, Color(0xFF9C27B0)),
                    Skill("Material Design", 0.95f, Color(0xFF9C27B0)),
                    Skill("Custom Views", 0.85f, Color(0xFF9C27B0)),
                    Skill("Jetpack Compose", 0.90f, Color(0xFF9C27B0))
                ),
                icon = Icons.Default.Palette,
                accentColor = Color(0xFF9C27B0)
            ),
            SkillCategory(
                title = "Tools",
                skills = listOf(
                    Skill("Android Studio", 0.95f, Color(0xFF00BCD4)),
                    Skill("Gradle", 0.85f, Color(0xFF00BCD4)),
                    Skill("Android Profiler", 0.85f, Color(0xFF00BCD4)),
                    Skill("Git", 0.90f, Color(0xFF00BCD4))
                ),
                icon = Icons.Default.Build,
                accentColor = Color(0xFF00BCD4)
            ),
            SkillCategory(
                title = "Testing",
                skills = listOf(
                    Skill("JUnit", 0.90f, Color(0xFFE91E63)),
                    Skill("Espresso", 0.85f, Color(0xFFE91E63)),
                    Skill("Mockito", 0.85f, Color(0xFFE91E63)),
                    Skill("Robolectric", 0.75f, Color(0xFFE91E63))
                ),
                icon = Icons.Default.BugReport,
                accentColor = Color(0xFFE91E63)
            ),
            SkillCategory(
                title = "Connectivity",
                skills = listOf(
                    Skill("BLE", 0.85f, Color(0xFF3F51B5)),
                    Skill("Wi-Fi", 0.85f, Color(0xFF3F51B5)),
                    Skill("WebSocket", 0.80f, Color(0xFF3F51B5)),
                    Skill("Retrofit", 0.95f, Color(0xFF3F51B5))
                ),
                icon = Icons.Default.Wifi,
                accentColor = Color(0xFF3F51B5)
            ),
            SkillCategory(
                title = "Debugging",
                skills = listOf(
                    Skill("Lifecycle Issues", 0.90f, Color(0xFFFF9800)),
                    Skill("Memory Leaks", 0.90f, Color(0xFFFF9800)),
                    Skill("ANR Analysis", 0.85f, Color(0xFFFF9800)),
                    Skill("Performance Tuning", 0.90f, Color(0xFFFF9800))
                ),
                icon = Icons.Default.Memory,
                accentColor = Color(0xFFFF9800)
            ),
            SkillCategory(
                title = "Leadership",
                skills = listOf(
                    Skill("Technical Leadership", 0.85f, Color(0xFF009688)),
                    Skill("Mentorship", 0.90f, Color(0xFF009688)),
                    Skill("Agile Methodologies", 0.85f, Color(0xFF009688)),
                    Skill("Code Reviews", 0.95f, Color(0xFF009688))
                ),
                icon = Icons.Default.Group,
                accentColor = Color(0xFF009688)
            ),
            SkillCategory(
                title = "Soft Skills",
                skills = listOf(
                    Skill("Team Collaboration", 0.95f, Color(0xFF673AB7)),
                    Skill("Adaptability", 0.90f, Color(0xFF673AB7)),
                    Skill("Continuous Learning", 0.95f, Color(0xFF673AB7))
                ),
                icon = Icons.Default.EmojiPeople,
                accentColor = Color(0xFF673AB7)
            )
        )
    }

    var isCircularLayout by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CompactAnimatedHeader()
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Skills & Expertise",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            
            // Toggle Button
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable { isCircularLayout = !isCircularLayout }
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = if (isCircularLayout) Icons.Default.KeyboardArrowDown else Icons.Default.Speed,
                    contentDescription = "Toggle Layout",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        AnimatedContent(
            targetState = isCircularLayout,
            transitionSpec = {
                fadeIn(animationSpec = tween(500)) togetherWith fadeOut(animationSpec = tween(500))
            },
            label = "LayoutSwitch"
        ) { circular ->
            if (circular) {
                CircularSkillsMenu(skillCategories)
            } else {
                ListViewSkills(skillCategories)
            }
        }
    }
}

@Composable
fun ListViewSkills(skillCategories: List<SkillCategory>) {
    var selectedCategory by remember { mutableStateOf<Int?>(null) }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(skillCategories) { index, category ->
            ExpandableSkillCard(
                category = category,
                isExpanded = selectedCategory == index,
                onToggle = {
                    selectedCategory = if (selectedCategory == index) null else index
                },
                index = index
            )
        }
    }
}

@Composable
fun CircularSkillsMenu(categories: List<SkillCategory>) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val rotationAnimatable = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    
    // Track center of the circle for drag calculations
    var center by remember { mutableStateOf(androidx.compose.ui.geometry.Offset.Zero) }
    
    val stepAngle = 360f / categories.size

    fun animateToNearest(targetIndex: Int) {
        scope.launch {
            val currentRotation = rotationAnimatable.value
            val targetRotation = -targetIndex * stepAngle
            
            // Shortest path logic
            var diff = (targetRotation - currentRotation) % 360f
            if (diff > 180f) diff -= 360f
            if (diff < -180f) diff += 360f
            
            rotationAnimatable.animateTo(
                targetValue = currentRotation + diff,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                center = androidx.compose.ui.geometry.Offset(
                    it.size.width / 2f,
                    it.size.height / 2f
                )
            }
            .pointerInput(categories.size) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val touchAngle = atan2(
                            y = change.position.y - center.y,
                            x = change.position.x - center.x
                        ) * (180f / PI.toFloat())
                        
                        val prevTouchAngle = atan2(
                            y = (change.position.y - dragAmount.y) - center.y,
                            x = (change.position.x - dragAmount.x) - center.x
                        ) * (180f / PI.toFloat())
                        
                        var delta = touchAngle - prevTouchAngle
                        if (delta > 180f) delta -= 360f
                        if (delta < -180f) delta += 360f
                        
                        scope.launch {
                            rotationAnimatable.snapTo(rotationAnimatable.value + delta)
                        }
                    },
                    onDragEnd = {
                        val currentRotation = rotationAnimatable.value
                        // Calculate target index based on current total rotation
                        val targetIndex = ((-currentRotation / stepAngle).roundToInt() % categories.size + categories.size) % categories.size
                        selectedIndex = targetIndex
                        animateToNearest(targetIndex)
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        val radius = minOf(maxWidth, maxHeight) / 2.5f
        val density = LocalDensity.current
        val radiusPx = with(density) { radius.toPx() }

        // Draw Outer Decorative Ring
        Canvas(modifier = Modifier.size(radius * 2.2f)) {
            drawCircle(
                color = Color.Gray.copy(alpha = 0.1f),
                style = Stroke(width = 2.dp.toPx())
            )
        }

        // Central Content
        Box(
            modifier = Modifier
                .size(maxWidth * 0.65f)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = categories[selectedIndex],
                transitionSpec = {
                    (scaleIn(initialScale = 0.8f) + fadeIn()).togetherWith(scaleOut(targetScale = 0.8f) + fadeOut())
                },
                label = "SelectedCategory"
            ) { category ->
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        category.icon,
                        contentDescription = null,
                        tint = category.accentColor,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        category.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Show top 4 skills in the center
                    category.skills.take(4).forEach { skill ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                skill.name,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                            Text(
                                "${(skill.level * 100).toInt()}%",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = category.accentColor
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(CircleShape)
                                .background(Color.Gray.copy(alpha = 0.1f))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(skill.level)
                                    .fillMaxHeight()
                                    .background(category.accentColor)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        // Circular Icons
        categories.forEachIndexed { index, category ->
            val angleDeg = rotationAnimatable.value + (index * stepAngle)
            val angleRad = angleDeg * (PI.toFloat() / 180f)
            
            val x = (radiusPx * cos(angleRad)).roundToInt()
            val y = (radiusPx * sin(angleRad)).roundToInt()

            val isSelected = index == selectedIndex
            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.5f else 1f,
                label = "iconScale"
            )
            
            Box(
                modifier = Modifier
                    .offset { IntOffset(x, y) }
                    .size(50.dp)
                    .scale(scale)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) category.accentColor else MaterialTheme.colorScheme.surface
                    )
                    .clickable {
                        selectedIndex = index
                        animateToNearest(index)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    category.icon,
                    contentDescription = category.title,
                    tint = if (isSelected) Color.White else category.accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun ExpandableSkillCard( category: SkillCategory,
                         isExpanded: Boolean,
                         onToggle: () -> Unit,
                         index: Int
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(index * 50L)
        visible = true
    }

    val cardHeight by animateDpAsState(
        targetValue = if (isExpanded) {
            (180 + (category.skills.size * 45)).dp
        } else {
            80.dp
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "cardHeight"
    )

    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(300),
        label = "rotation"
    )

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { it / 2 },
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
        ) + fadeIn()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onToggle() },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = if (isExpanded) 8.dp else 4.dp
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(
                                            category.accentColor.copy(alpha = 0.3f),
                                            category.accentColor.copy(alpha = 0.1f)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                category.icon,
                                contentDescription = null,
                                tint = category.accentColor,
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        Column {
                            Text(
                                category.title,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                "${category.skills.size} skills",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }

                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = category.accentColor,
                        modifier = Modifier
                            .size(28.dp)
                            .rotate(rotation)
                    )
                }

                // Expanded content - Skills with progress bars
                AnimatedVisibility(
                    visible = isExpanded,
                    enter = fadeIn(animationSpec = tween(300, delayMillis = 100)) +
                            expandVertically(animationSpec = tween(300)),
                    exit = fadeOut(animationSpec = tween(200)) +
                            shrinkVertically(animationSpec = tween(200))
                ) {
                    Column(
                        modifier = Modifier.padding(top = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        category.skills.forEach { skill ->
                            SkillProgressItem(
                                skillName = skill.name,
                                progress = skill.level,
                                color = skill.color
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SkillProgressItem(skillName: String, progress: Float, color: Color) {
    var animatedProgress by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        delay(100)
        animatedProgress = progress
    }

    val animatedValue by animateFloatAsState(
        targetValue = animatedProgress,
        animationSpec = tween(1000, easing = FastOutSlowInEasing),
        label = "progress"
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                skillName,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            Text(
                "${(animatedValue * 100).toInt()}%",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Gray.copy(alpha = 0.2f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedValue)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                color,
                                color.copy(alpha = 0.7f)
                            )
                        )
                    )
            )
        }
    }
}
