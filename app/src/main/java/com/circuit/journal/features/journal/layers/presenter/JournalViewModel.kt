package com.circuit.journal.features.journal.layers.presenter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.circuit.journal.composables.journal.components.TransformationConfig
import com.circuit.journal.features.journal.layers.domain.model.Journal
import com.circuit.journal.features.journal.layers.domain.usecase.ConvertToHtmlUseCase
import com.circuit.journal.features.journal.layers.domain.usecase.GetJournalByIdUseCase
import com.circuit.journal.features.journal.layers.domain.usecase.GetSavedJournalsPagingSourceUseCase
import com.circuit.journal.features.journal.layers.domain.usecase.GetUnsavedJournalUseCase
import com.circuit.journal.features.journal.layers.domain.usecase.InsertJournalUseCase
import com.circuit.journal.features.journal.layers.domain.usecase.SaveJournalUseCase
import com.circuit.journal.features.journal.utils.exceptions.JournalBlankTextException
import com.circuit.journal.navigation.KEY_JOURNAL_ID
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JournalViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val insertJournalUseCase: InsertJournalUseCase,
    private val saveJournalUseCase: SaveJournalUseCase,
    private val getUnsavedJournalUseCase: GetUnsavedJournalUseCase,
    private val getJournalByIdUseCase: GetJournalByIdUseCase,
    private val getSavedJournalsPagingSourceUseCase: GetSavedJournalsPagingSourceUseCase,
    private val convertToHtmlUseCase: ConvertToHtmlUseCase,
) : ViewModel() {

    private val journalId = savedStateHandle.get<Long>(KEY_JOURNAL_ID)
    val journals = getSavedJournalsPagingSourceUseCase().cachedIn(viewModelScope)

    private val _state = MutableStateFlow(createState())

    /**
     * Exposed state
     */
    val state = _state.asStateFlow()


    private val _action = Channel<JournalAction>()

    /**
     * Exposed flow to send one-time action events
     */
    val action = _action.receiveAsFlow()

    init {
        journalId
            ?.let { getJournalById(it) }
            ?: getUnsavedJournal()
    }

    /**
     * Handle to process any business logic
     */
    fun onEvent(event: JournalEvent) {
        when (event) {
            is OnSaveClicked -> {
                saveJournal(event.journal)
            }

            is OnShareClicked -> {
                convertToHtmlAndShare(event.journal, event.transformationConfigs)
            }

            is OnJournalTextChange -> {
                updateJournalText(event.text)
            }

            is OnJournalSelected -> {

            }

            OnHtmlSendError -> {
                onHtmlSendFailed()
            }
        }
    }

    private fun saveJournal(journal: Journal) {
        saveJournalUseCase(journal)
            .onEach {
                journal.savedAt ?: getUnsavedJournal()
            } //Reset text if the journal wasn't saved previously(project requirement)
            .onCompletion { _action.send(OnMessage("The journal was saved")) }
            .catch { onSaveFailed(it) }
            .launchIn(viewModelScope)
    }

    private fun convertToHtmlAndShare(
        journal: Journal,
        transformationConfigs: List<TransformationConfig>
    ) {
        convertToHtmlUseCase(journal.text, transformationConfigs)
            .onStart {
                _state.update { it.copy(isLoading = true) }
            }
            .onEach {
                _state.update { it.copy(isLoading = false) }
                _action.send(OnHtmlGenerated(it))
            }
            .catch {
                handleError(it, "Failed to generate HTML")
            }
            .launchIn(viewModelScope)

    }


    private fun updateJournalText(text: String) {
        _state.update { it.copy(journal = it.journal?.copy(text = text)) }
        _state.value.journal?.let {
            insertJournalUseCase(it.copy(text = text))
                .launchIn(viewModelScope)
        }
    }

    private fun getUnsavedJournal() {
        process {
            getUnsavedJournalUseCase()
                .onEach { it ?: insertJournalUseCase(createDummyJournal()).first() }
                .filterNotNull()
        }
    }

    private fun getJournalById(id: Long) {
        process { getJournalByIdUseCase(id) }
    }

    private fun process(action: () -> Flow<Journal>) {
        viewModelScope.launch {
            action()
                .onStart {
                    _state.update { it.copy(isLoading = true) }
                }
                .onEach { journal ->
                    _state.update { it.copy(isLoading = false, journal = journal) }
                }
                .catch {
                    it.printStackTrace()
                    _state.update { it.copy(isLoading = false) }
                }
                .first()
        }
    }

    private fun onHtmlSendFailed() {
        viewModelScope.launch {
            _action.send(OnMessage("Failed to send HTML"))
        }
    }

    private suspend fun onSaveFailed(it: Throwable) {
        val message = (it as? JournalBlankTextException)?.message ?: "Failed to save the journal"
        handleError(it, message)
    }

    private suspend fun handleError(throwable: Throwable, message: String) {
        throwable.printStackTrace()
        _state.update { it.copy(isLoading = false) }
        _action.send(OnMessage(message))
    }

    private fun createState() = JournalState(
        journal = null,
        isLoading = true,
        journals = journals
    )

    private fun createDummyJournal() = Journal(
        id = 0L,
        savedAt = null,
        text = ""
    )
}

/**
 * Interface defining one-time action events sent by ViewModel to be executed on UI
 */
sealed interface JournalAction
data class OnMessage(val message: String) : JournalAction
data class OnHtmlGenerated(val html: String) : JournalAction

data class JournalState(
    val journal: Journal?,
    val isLoading: Boolean,
    val journals: Flow<PagingData<Journal>>
)

/**
 * UI events that trigger view model functions
 */
sealed interface JournalEvent
data class OnSaveClicked(val journal: Journal) : JournalEvent
data class OnShareClicked(
    val journal: Journal,
    val transformationConfigs: List<TransformationConfig>
) : JournalEvent

data class OnJournalTextChange(val text: String) : JournalEvent
data class OnJournalSelected(val journal: Journal) : JournalEvent
data object OnHtmlSendError:JournalEvent

