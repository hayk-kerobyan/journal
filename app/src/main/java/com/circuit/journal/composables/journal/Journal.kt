package com.circuit.journal.composables.journal

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.circuit.journal.common.theme.JournalTheme
import com.circuit.journal.composables.journal.components.JournalTextVisualTransformer
import com.circuit.journal.composables.journal.components.TransformationConfig
import com.circuit.journal.composables.journal.defaults.BoldTransformationConfig
import com.circuit.journal.composables.journal.defaults.HeadlineTransformationConfig

/**
 * A BasicTextField that transforms the text styling according to received [TransformationConfig].
 * Styling transformations are applied with the help of [JournalTextVisualTransformer] class.
 *
 * @param value the input [String] text to be shown in the text field
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param modifier optional [Modifier] for this text field.
 * @param textStyle Style configuration that applies at character level such as color, font etc.
 * @param transformationConfigs Config classes that define text visual transformation rules
 *
 * @see [TransformationConfig]
 * @see [JournalTextVisualTransformer]
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
            JournalTextVisualTransformer(
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

@Preview(showBackground = true)
@Composable
fun JournalPreview() {
    JournalTheme {
        Journal(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            value = ">This is a Headline\n" +
                    "\n" +
                    "The last 3 words of line [must be bold]\n" +
                    "And this is an ordinary line\n" +
                    "\n" +
                    "[Note:] You can add or remove any config in transformationConfigs param" +
                    "and the text will behave respectively\n" +
                    "\n" +
                    "Such custom configs should be stored in [defaults] folder",
            onValueChange = {},
            transformationConfigs = listOf(
                HeadlineTransformationConfig,
                BoldTransformationConfig
            )
        )
    }
}
