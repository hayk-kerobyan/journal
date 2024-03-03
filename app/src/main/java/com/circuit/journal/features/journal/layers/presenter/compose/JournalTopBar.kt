package com.circuit.journal.features.journal.layers.presenter.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalTopBar(
    onMenuClick: () -> Unit,
    onSaveClick: () -> Unit,
    onShareClick: () -> Unit,
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu icon")
            }
        },
        actions = {
            IconButton(onClick = onShareClick) {
                Icon(imageVector = Icons.Default.Share, contentDescription = "Share icon")
            }
            IconButton(onClick = onSaveClick) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Done icon")
            }
        }
    )
}