package com.circuit.journal.ui.features.journal.presenter.util.composables.journal

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.circuit.journal.ui.features.journal.presenter.util.composables.journal.components.JournalTextTransformer
import com.circuit.journal.ui.features.journal.presenter.util.composables.journal.components.TransformationConfig

/**
 * A BasicTextField that transforms the text styling according to received [TransformationConfig].
 * Styling transformations are applied with the help of [JournalTextTransformer] class.
 *
 * @param value the input [String] text to be shown in the text field
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param modifier optional [Modifier] for this text field.
 * @param textStyle Style configuration that applies at character level such as color, font etc.
 * @param transformationConfigs Config classes that define text visual transformation rules
 *
 * @see [TransformationConfig]
 * @see [JournalTextTransformer]
 */
@Composable
fun Journal(
    value: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    onValueChange: (String) -> Unit = {},
    transformationConfigs: List<TransformationConfig>
) {

    val textTransformation by remember {
        mutableStateOf(
            JournalTextTransformer(
                textStyle.toSpanStyle(),
                transformationConfigs
            )
        )
    }

    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        visualTransformation = textTransformation,
        textStyle = textStyle,
    )
}
