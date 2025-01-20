package org.magomedov.listcase.core

import androidx.compose.material.DropdownMenu
import androidx.compose.runtime.Composable
import io.github.alexzhirkevich.cupertino.CupertinoDropdownMenu
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import org.magomedov.listcase.Platform
import org.magomedov.listcase.System

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun AdaptiveDropdownMenu(
    platform: Platform,
    expended: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    when(platform.system) {
        System.IOS -> {
            CupertinoDropdownMenu(
                expanded = expended,
                onDismissRequest = onDismissRequest,
                content = {
                    content()
                }
            )
        }
        System.ANDROID -> {
            DropdownMenu(
                onDismissRequest = onDismissRequest,
                expanded = expended,
                content = {
                    content()
                }
            )
        }
    }
}