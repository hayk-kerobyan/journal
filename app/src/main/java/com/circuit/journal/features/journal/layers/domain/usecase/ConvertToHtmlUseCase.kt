package com.circuit.journal.features.journal.layers.domain.usecase

import androidx.core.util.forEach
import com.circuit.journal.composables.journal.components.TransformationConfig
import com.circuit.journal.composables.journal.components.calculateStyledSegment
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class ConvertToHtmlUseCase(
    private val context: CoroutineContext,
) {
    operator fun invoke(text: String, configs: List<TransformationConfig>) = flow {
        val indexes = mutableListOf<Int>()
        configs
            .map { calculateStyledSegment(text, it.openingChar, it.closingChar) }
            .onEach {
                it.forEach { key, value -> indexes.add(key); indexes.add(value) }
            }
        val html = buildString {
            text.forEachIndexed { index, char ->
                if (indexes.contains(index)) {
                    configs
                        .firstOrNull { it.openingChar == char }
                        ?.let {
                            append(it.openingHtml)
                            if (it.showOpeningChar) append(char)
                        }

                    configs.firstOrNull { it.closingChar == char }
                        ?.let {
                            append(it.closingHtml)
                            if (it.showClosingChar) append(char)
                        }
                } else {
                    append(char)
                }
            }
        }
        emit(html)
    }.flowOn(context)
}

