package com.samm.expense_tracker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val PremiumColorScheme =
    darkColorScheme(

        primary = Emerald,

        secondary = Gold,

        background = PremiumNavy,

        surface = CardDark,

        onPrimary = PremiumNavy,

        onSecondary = PremiumNavy,

        onBackground = PremiumWhite,

        onSurface = PremiumWhite,

        error = ErrorRed
    )


@Composable
fun ExpenseTrackerTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = PremiumColorScheme,
        typography = Typography,
        content = content
    )
}