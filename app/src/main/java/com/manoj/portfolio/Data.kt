package com.manoj.portfolio

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.ui.graphics.Color

val cards =
    listOf(
        CardStackItem(
            1,
            Icons.Default.Info,
            "About Me",
            "Passionate Android developer with a love for creating beautiful, performant apps. I specialize in Jetpack Compose and modern Android architecture.",
            Color(0xFF6C63FF)
        ),
        CardStackItem(
            2,
            Icons.Default.LocationOn,
            "Location",
            "Madurai, Tamil Nadu",
            Color(0xFFFF6584)
        ),
        CardStackItem(
            3,
            Icons.Default.Phone,
            "Contact",
            "+91-8428724357",
            Color(0xFFA1F589)
        ),
        CardStackItem(
            4,
            Icons.Default.Email,
            "Mail",
            "contact.mano95@gmail.com",
            Color(0xFF4ECDC4)
        ),
        CardStackItem(
            5,
            Icons.Default.Link,
            "Connection",
            "https://www.linkedin.com/in/manoj-kumar-r-android",
            Color(0xFFFFA500)
        ),
        CardStackItem(
            6,
            Icons.Default.Create,
            "My Works",
            "https://github.com/mano-kotdev",
            Color(0xFF9C27B0)
        )
    )

