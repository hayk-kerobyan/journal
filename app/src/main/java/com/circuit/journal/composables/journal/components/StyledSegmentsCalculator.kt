package com.circuit.journal.composables.journal.components

import android.util.SparseIntArray


/**
 * Calculates the segments of text that fall between opening and closing chars. Segments are
 * represented by [SparseIntArray] which contains ranges of chars
 *
 * @param text text subject for stylization
 * @param openingChar the key char marking the start of style application to the text segment
 * @param closingChar the key char marking the end of style application to the text segment
 *
 * Returns [SparseIntArray]
 *  key - start of styled segment
 *  value - end of styled segment
 */
fun calculateStyledSegment(text: String, openingChar: Char, closingChar: Char): SparseIntArray {
    var segmentStartedAt = -1
    val styledSegments = SparseIntArray()
    for (i in text.indices) {
        when (text[i]) {
            openingChar -> {
                if (segmentStartedAt == -1) {
                    segmentStartedAt = i
                }
            }

            closingChar -> {
                if (segmentStartedAt != -1) {
                    styledSegments.append(segmentStartedAt, i)
                    segmentStartedAt = -1
                }
            }

            else -> {}
        }
    }
    return styledSegments
}