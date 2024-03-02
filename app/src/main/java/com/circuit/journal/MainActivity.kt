package com.circuit.journal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.circuit.journal.common.theme.JournalTheme
import com.circuit.journal.navigation.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JournalTheme {
                Navigation()
            }
        }
    }
}

