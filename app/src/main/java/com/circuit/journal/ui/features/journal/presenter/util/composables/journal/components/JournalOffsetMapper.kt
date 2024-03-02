package com.circuit.journal.ui.features.journal.presenter.util.composables.journal.components

import androidx.compose.ui.text.input.OffsetMapping

/**
 * Calculates the omitted chars in the text and adjusts cursor position respectively
 */
class JournalOffsetMapper(private val numOfCharsDeleted: BooleanArray) : OffsetMapping {

    override fun originalToTransformed(offset: Int): Int {
        var numOfSkipped = 0
        for (i in 0 until offset) {
            if (numOfCharsDeleted[i])
                numOfSkipped++
        }
        return offset - numOfSkipped
    }

    override fun transformedToOriginal(offset: Int): Int {
        var numOfSkipped = 0
        for (i in 1..offset) {
            if (numOfCharsDeleted[i - 1])
                numOfSkipped++
        }
        return offset + numOfSkipped
    }
}