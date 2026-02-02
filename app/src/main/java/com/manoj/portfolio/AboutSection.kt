package com.manoj.portfolio

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.manoj.portfolio.ui.theme.PortfolioTheme
import kotlinx.coroutines.delay

data class AboutCard(
    val id: Int,
    val icon: ImageVector,
    val title: String,
    val content: String,
    val accentColor: Color
)

@Composable
fun AboutSection() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F0E17),
                        Color(0xFF1A1A2E)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CompactAnimatedHeader()
            CardStack()
        }
    }
}

@Composable
fun CompactAnimatedHeader() {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ) + fadeIn()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompactPulsingProfileCircle()
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        "Manoj Kumar R",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "Senior Android Developer",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }
        }
    }
}

@Composable
fun CompactPulsingProfileCircle() {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    Box(
        modifier = Modifier
            .size(70.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    listOf(
                        Color(0xFFFF6584),
                        Color(0xFF6C63FF)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Default.Person,
            contentDescription = "Manoj",
            modifier = Modifier.size(40.dp),
            tint = Color.White
        )
    }
}

@Composable
fun CardStack() {
    val cards = remember {
        listOf(
            AboutCard(
                1,
                Icons.Default.Info,
                "About Me",
                "Passionate Android developer with a love for creating beautiful, performant apps. I specialize in Jetpack Compose and modern Android architecture.",
                Color(0xFF6C63FF)
            ),
            AboutCard(
                2,
                Icons.Default.LocationOn,
                "Location",
                "Madurai, Tamil Nadu",
                Color(0xFFFF6584)
            ),
            AboutCard(
                3,
                Icons.Default.Phone,
                "Contact",
                "+91-8428724357",
                Color(0xFFA1F589)
            ),
            AboutCard(
                4,
                Icons.Default.Email,
                "Mail",
                "contact.mano95@gmail.com",
                Color(0xFF4ECDC4)
            ),
            AboutCard(
                5,
                Icons.Default.Link,
                "Connection",
                "https://www.linkedin.com/in/manoj-kumar-r-android",
                Color(0xFFFFA500)
            ),
            AboutCard(
                6,
                Icons.Default.Create,
                "My Works",
                "https://github.com/mano-kotdev",
                Color(0xFF9C27B0)
            )
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var direction by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp), contentAlignment = Alignment.Center
    ) {
        cards.take(3).reversed().forEachIndexed { rIndex, card ->
            val index = 2 - rIndex
            val actualCardIndex = (currentIndex + index) % cards.size
            val scale = 1f - (index * 0.05f)
            val offsetY = index * 12.dp

            Box(
                modifier = Modifier
                    .scale(scale)
                    .offset(y = offsetY)
            ) {
                StackCard(
                    card = cards[actualCardIndex],
                    isTop = index == 0,
                    onSwipeLeft = {
                        if (index == 0) {
                            direction = -1
                            currentIndex = (currentIndex + direction + cards.size) % cards.size
                        }
                    },
                    onSwipeRight = {
                        if (index == 0) {
                            direction = 1
                            currentIndex = (currentIndex + direction) % cards.size
                        }
                    }
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 2.dp)
        ) {
            CardCounter(
                currentIndex = currentIndex,
                totalCards = cards.size
            )
        }
    }
}

@Composable
fun CardCounter(currentIndex: Int, totalCards: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalCards) { index ->
            val isSelected = index == currentIndex
            Box(
                modifier = Modifier
                    .size(
                        width = if (isSelected) 24.dp else 8.dp,
                        height = 8.dp
                    )
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        if (isSelected)
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF6C63FF),
                                    Color(0xFF4ECDC4)
                                )
                            )
                        else
                            SolidColor(Color.Gray.copy(alpha = 0.3f))
                    )
            )
        }
    }
}

@Composable
fun StackCard(
    card: AboutCard,
    isTop: Boolean,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(card.id) {
        offsetX = 0f
        visible = false
        delay(50)
        visible = true
    }
    val rotation by animateFloatAsState(
        targetValue = (offsetX / 30).coerceIn(-15f, 15f),
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "rotation"
    )
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(
            initialScale = 0.8f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        ) + fadeIn(),
        exit = fadeOut()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .offset(x = offsetX.dp)
                .rotate(rotation)
                .pointerInput(Unit) {
                    if (isTop) {
                        detectDragGestures(
                            onDragEnd = {
                                if (offsetX > 100) {
                                    onSwipeRight()
                                } else if (offsetX < -100) {
                                    onSwipeLeft()
                                }
                                offsetX = 0f
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                offsetX += dragAmount.x
                            }
                        )
                    }
                },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = if (isTop) 16.dp else 8.dp
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    card.accentColor,
                                    card.accentColor.copy(alpha = 0.6f)
                                )
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        card.accentColor.copy(alpha = 0.3f),
                                        card.accentColor.copy(alpha = 0.1f)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            card.icon,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = card.accentColor
                        )
                    }

                    Text(
                        card.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        card.content,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    if (isTop) {
                        SwipeHint()
                    }
                }
            }
        }
    }
}

@Composable
fun SwipeHint() {
    val infiniteTransition = rememberInfiniteTransition(label = "swipe_hint")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.alpha(alpha)
    ) {
        Icon(
            Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
        Text(
            "Swipe to explore",
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
        Icon(
            Icons.Default.ArrowForward,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview
@Composable
fun AboutSectionPreview() {
    PortfolioTheme {
        AboutSection()
    }
}