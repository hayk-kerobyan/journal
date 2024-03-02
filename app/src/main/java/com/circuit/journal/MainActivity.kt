package com.circuit.journal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.circuit.journal.ui.features.journal.presenter.util.composables.journal.Journal
import com.circuit.journal.ui.features.journal.presenter.util.composables.journal.defaults.BoldTransformationConfig
import com.circuit.journal.ui.features.journal.presenter.util.composables.journal.defaults.HeadlineTransformationConfig
import com.circuit.journal.ui.theme.JournalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JournalTheme {
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JournalTheme {
        Journal(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            value = ">This is a Headline\n" +
                    "\n" +
                    "The last 3 words of line [must be bold]\n" +
                    "And this is an ordinary line\n" +
                    "\n" +
                    "[Note:] You can add or remove any config in transformationConfigs param" +
                    "and the text will behave respectively\n" +
                    "\n" +
                    "Such custom configs should be stored in [defaults] folder",
            onValueChange = {},
            transformationConfigs = listOf(
                HeadlineTransformationConfig,
                BoldTransformationConfig
            )
        )
    }
}