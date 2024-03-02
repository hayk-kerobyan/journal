package com.circuit.journal.composables.journal.defaults

import android.util.SparseIntArray
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import com.circuit.journal.composables.journal.components.TransformationConfig

val BoldTransformationConfig = TransformationConfig(
    transformedRangeCalculation = { calculateBoldSegments(it) },
    shouldSkip = { currentPos, startPos, endPos -> currentPos == startPos || currentPos == endPos }, //skipping '[' and ']' symbols
    style = SpanStyle(fontWeight = FontWeight.Bold)
)

const val BOLD_TEXT_OPENING_CHAR = '['
const val BOLD_TEXT_CLOSING_CHAR = ']'

/**
 * Calculates the ranges of bold segments within text. The start of each segment is
 * [BOLD_TEXT_OPENING_CHAR] which ends with [BOLD_TEXT_CLOSING_CHAR]
 *
 * Returns [SparseIntArray]
 *  key - start of bold segment
 *  value - end of bold segment
 */
fun calculateBoldSegments(text: String): SparseIntArray {
    var boldStart = -1
    val boldSegments = SparseIntArray()
    for (i in text.indices) {
        when (text[i]) {
            BOLD_TEXT_OPENING_CHAR -> {
                if (boldStart == -1) {
                    boldStart = i
                }
            }

            BOLD_TEXT_CLOSING_CHAR -> {
                if (boldStart != -1) {
                    boldSegments.append(boldStart, i)
                    boldStart = -1
                }
            }

            else -> {}
        }
    }
    return boldSegments
}
