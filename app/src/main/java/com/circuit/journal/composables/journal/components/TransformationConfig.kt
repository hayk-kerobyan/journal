package com.circuit.journal.composables.journal.components

import androidx.compose.ui.text.SpanStyle

/**
 * Simple data class that contains text style transformation rules
 *
 * @param style Span style to be applied for the text segments between [openingChar] and [closingChar]
 * @param openingChar special char which will be the beginning of styled segment
 * @param closingChar special char which will be the end of styled segment
 * @param showOpeningChar if true, the opening character will be visible and hidden otherwise
 * @param showClosingChar if true, the closing character will be visible and hidden otherwise
 */
data class TransformationConfig(
    val style: SpanStyle,
    val openingChar: Char,
    val closingChar: Char,
    val showOpeningChar: Boolean,
    val showClosingChar: Boolean
)