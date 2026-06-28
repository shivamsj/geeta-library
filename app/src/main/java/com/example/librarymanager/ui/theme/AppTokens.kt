package com.example.librarymanager.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

internal val Orange = Color(0xFF0A43A3)
internal val OrangeDark = Color(0xFF061F62)
internal val OrangeSoft = Color(0xFF1763C8)
internal val Green = Color(0xFF00B825)
internal val Blue = Color(0xFF0C95B8)
internal val Pink = Color(0xFFE5006E)
internal val Border = Color(0xFFE4E7EC)
internal val TextDark = Color(0xFF1C2430)
internal val Muted = Color(0xFF7B8494)
internal val RoyalBlue = Color(0xFF063BAA)
internal val DeepNavy = Color(0xFF061F62)
internal val LibraryGold = Color(0xFFD9A52A)

internal fun orangeGradient(): Brush {
    return Brush.linearGradient(listOf(OrangeSoft, Orange, OrangeDark))
}
