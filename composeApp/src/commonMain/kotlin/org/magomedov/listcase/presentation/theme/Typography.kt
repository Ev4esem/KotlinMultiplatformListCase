package org.magomedov.listcase.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import kotlinmultiplatformlistcase.composeapp.generated.resources.Res
import kotlinmultiplatformlistcase.composeapp.generated.resources.Roboto_Regular
import org.jetbrains.compose.resources.Font

@Composable
fun robotoFamily() = FontFamily(
    Font(
        resource = Res.font.Roboto_Regular,
        weight = FontWeight.Medium
    )
)