package com.circuit.journal.ui.features.journal.presenter.util.composables.journal.defaults

import android.util.SparseIntArray
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.sp
import com.circuit.journal.ui.features.journal.presenter.util.composables.journal.components.TransformationConfig


val HeadlineTransformationConfig = TransformationConfig(
    transformedRangeCalculation = { calculateLargeLines(it) },
    shouldSkip = { currentPos, startPos, _ -> currentPos == startPos }, //skipping '>' symbol
    style = SpanStyle(fontSize = 32.sp)
)

const val HEADLINE_CHAR = '>'

/**
 * Calculates the ranges of headline chars within text. The start of each headline is any new
 * line starting with [HEADLINE_CHAR] and ending with new line
 *
 * Returns [SparseIntArray]
 *  key - text index (start of Headline)
 *  value - text index (end of Headline)
 */
fun calculateLargeLines(text: String): SparseIntArray {
    val largeLineSegments = SparseIntArray()
    var isOpen = -1
    for (i in text.indices) {
        if (text[i] == HEADLINE_CHAR && (i == 0 || text[i - 1] == '\n')) {
            isOpen = i
        }
        if (text[i] == '\n' && isOpen != -1) {
            largeLineSegments.append(isOpen, i)
            isOpen = -1
        }
    }
    return largeLineSegments
}
