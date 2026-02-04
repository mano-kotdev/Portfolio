package com.manoj.portfolio

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import kotlinx.coroutines.delay

/**
 * CardStack Component
 *
 * A swipeable card stack component inspired by modern design systems.
 * Features smooth animations, gesture support, and a premium feel.
 *
 * Perfect for:
 * - Onboarding flows
 * - Feature showcases
 * - Tutorial walkthroughs
 *
 * @author Manoj Kumar R
 * @see https://www.linkedin.com/in/manoj-kumar-r-android
 */


/**
 * Data model for a card in the stack
 *
 * @param id Unique identifier for the card
 * @param icon Material icon to display
 * @param title Card title/heading
 * @param content Card description/content
 * @param accentColor Primary color for the card theme
 */
data class CardStackItem(
    val id: Int,
    val icon: ImageVector,
    val title: String,
    val content: String,
    val accentColor: Color
)

/**
 * Main CardStack Composable
 *
 * Displays a stack of swipeable cards with depth effect.
 * Users can swipe left/right to navigate through cards.
 *
 * @param cards List of CardStackItem to display
 * @param modifier Modifier to be applied to the container
 *
 * Example usage:
 * ```
 * val cards = listOf(
 *     CardStackItem(
 *         id = 1,
 *         icon = Icons.Default.Info,
 *         title = "About Me",
 *         content = "Your description here",
 *         accentColor = Color(0xFF6C63FF)
 *     ),
 *     // ... more cards
 * )
 *
 * CardStack(cards = cards)
 * ```
 */
@Composable
fun CardStack(
    cards: List<CardStackItem>,
    modifier: Modifier = Modifier
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        // Background cards (stack effect) - show 3 cards max
        cards.take(3).reversed().forEachIndexed { reverseIndex, _ ->
            val index = 2 - reverseIndex
            val actualCardIndex = (currentIndex + index) % cards.size
            val scale = 1f - (index * 0.05f)
            val offsetY = -(index * 24).dp

            Box(
                modifier = Modifier
                    .scale(scale)
                    .offset(y = offsetY)
            ) {
                StackCard(
                    card = cards[actualCardIndex],
                    isTop = index == 0,
                    onSwipe = {
                        currentIndex = (currentIndex + 1) % cards.size
                    }
                )
            }
        }

        // Card counter indicator
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
        ) {
            CardCounter(
                currentIndex = currentIndex,
                totalCards = cards.size
            )
        }
    }
}


/**
 * Swipe Hint Indicator
 *
 * Shows a pulsing hint to the user that the card is swipeable.
 * Fades in and out continuously to draw attention.
 */
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
            Icons.AutoMirrored.Filled.ArrowBack,
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
            Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    }
}

/**
 * Individual Card in the Stack
 *
 * A single swipeable card with entrance animations, gesture support,
 * and visual design.
 *
 * Features:
 * - Swipe gestures (left/right)
 * - Rotation during drag
 * - Scale-in entrance animation
 * - Gradient accent bar
 * - Icon with radial gradient background
 *
 * @param card The CardStackItem data to display
 * @param isTop Whether this is the top (interactive) card
 * @param onSwipe Callback when card is swiped away
 */
@Composable
fun StackCard(
    card: CardStackItem,
    isTop: Boolean,
    onSwipe: () -> Unit
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
                .fillMaxHeight(0.8f)
                .offset(x = offsetX.dp)
                .rotate(rotation)
                .pointerInput(Unit) {
                    if (isTop) {
                        detectDragGestures(
                            onDragEnd = {
                                if (offsetX > 100 || offsetX < -100) {
                                    onSwipe()
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
                // Gradient accent bar at top
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

                    //Icon with gradient background
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
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Swipe hint - only show on top card
                    if (isTop) {
                        SwipeHint()
                    }
                }
            }
        }
    }
}


/**
 * Card Counter Indicator
 *
 * Shows dots/bars to indicate current position in the stack.
 * Active indicator is elongated and uses gradient.
 *
 * @param currentIndex Zero-based index of current card
 * @param totalCards Total number of cards in the stack
 */
@Composable
fun CardCounter(currentIndex: Int, totalCards: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
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