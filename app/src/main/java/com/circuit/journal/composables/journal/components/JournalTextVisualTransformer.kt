package com.circuit.journal.composables.journal.components

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.core.util.forEach

/**
 * Applies visual transformation of each text char according to [TransformationConfig]s received
 *
 * @param defaultStyle is applied to texts that are not subject for styling by received [transformationConfigs]
 * @param transformationConfigs each text char is checked against each transformationConfigs. Respective
 * config styling is being applied to the char if  the char falls within calculated styling ranges
 *
 *
 * @see TransformationConfig
 *
 */
class JournalTextVisualTransformer(
    private val defaultStyle: SpanStyle,
    private val transformationConfigs: List<TransformationConfig>
) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val textString = text.text
        val skippedIndices = BooleanArray(textString.length)

        //define text segments subject to transformation by each config
        val styledSegments = transformationConfigs
            .map { calculateStyledSegment(textString, it.openingChar, it.closingChar) }

        val transformed = buildAnnotatedString {
            for (i in textString.indices) { // iterate through each char
                var spanStyle = defaultStyle
                var skipChar = false

                //apply each transformation if the char is within the styled segment
                styledSegments
                    .forEachIndexed { index, transformationRanges ->
                        transformationRanges.forEach { startPos, endPos ->
                            if (i in startPos..endPos) {
                                if (!transformationConfigs[index].showOpeningChar && i == startPos
                                    || !transformationConfigs[index].showClosingChar && i == endPos
                                ) {
                                    skipChar = true
                                } else {
                                    spanStyle = spanStyle.merge(transformationConfigs[index].style)
                                }
                            }
                        }
                    }
                withStyle(spanStyle) {
                    if (skipChar) {
                        skippedIndices[i] = true
                    } else {
                        append(textString[i])
                    }
                }
            }
        }

        return TransformedText(
            text = transformed,
            offsetMapping = JournalCursorOffsetMapper(skippedIndices)
        )
    }
}