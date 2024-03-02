package com.circuit.journal.composables.journal.components

import android.util.SparseIntArray
import androidx.compose.ui.text.SpanStyle

/**
 * Simple data class that contains text style transformation rules
 *
 * @param transformedRangeCalculation defines the index ranges of text where respective styling
 * should be applied. The ranges are represented in form of [SparseIntArray] for efficiency. The
 * key represents the start index and value the end index of the range where [style] should be
 * applied
 * @param shouldSkip defines whether the char falling into transformation range should be hidden
 * or shown.
 * @param style to be applied for the defined text char index ranges
 */
data class TransformationConfig(
    val transformedRangeCalculation: (text: String) -> SparseIntArray,
    val shouldSkip: (currentPos: Int, startPos: Int, endPos: Int) -> Boolean,
    val style: SpanStyle
)