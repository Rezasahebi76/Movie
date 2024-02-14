package com.android.movie.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MovieBorderButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        ),
        shape = MaterialTheme.shapes.extraSmall,
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.onTertiaryContainer),
        content = content
    )
}

@Composable
fun MovieButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides MaterialTheme.typography.labelMedium,
        ) {
            content()
        }
    }
}