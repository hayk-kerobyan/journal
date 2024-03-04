package com.circuit.journal.composables.journal.defaults

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.sp
import com.circuit.journal.composables.journal.components.TransformationConfig

/**
 * Example headline styling config
 */
val HeadlineTransformationConfig = TransformationConfig(
    openingChar = '>',
    closingChar = '\n',
    showOpeningChar = false,
    showClosingChar = true,
    style = SpanStyle(fontSize = 32.sp),
    openingHtml = "<h1>",
    closingHtml = "</h1>"
)