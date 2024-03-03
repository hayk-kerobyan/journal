package com.circuit.journal.composables.journal.defaults

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import com.circuit.journal.composables.journal.components.TransformationConfig

/**
 * Example bold styling config
 */
val BoldTransformationConfig = TransformationConfig(
    openingChar = '[',
    closingChar = ']',
    showOpeningChar = false,
    showClosingChar = false,
    style = SpanStyle(fontWeight = FontWeight.Bold)
)