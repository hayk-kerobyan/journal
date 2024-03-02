package com.circuit.journal.features.journal.layers.presenter

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.circuit.journal.common.theme.JournalTheme
import com.circuit.journal.composables.journal.Journal
import com.circuit.journal.composables.journal.defaults.BoldTransformationConfig
import com.circuit.journal.composables.journal.defaults.HeadlineTransformationConfig

@Composable
fun JournalScreen(){
    var text by remember { mutableStateOf("") }
    Journal(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        value = text,
        onValueChange = { text = it },
        transformationConfigs = listOf(
            HeadlineTransformationConfig,
            BoldTransformationConfig
        )
    )
}

@Preview(showBackground = true)
@Composable
fun JournalScreenPreview() {
    JournalTheme {
        JournalScreen()
    }
}