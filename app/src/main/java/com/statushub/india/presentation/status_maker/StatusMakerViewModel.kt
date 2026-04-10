package com.statushub.india.presentation.status_maker

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.statushub.india.data.local.Quote
import com.statushub.india.data.repository.QuoteRepository
import com.statushub.india.presentation.status_maker.TemplateItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StatusMakerState(
    val step: Int = 1, // 1: Category, 2: Template, 3: Preview
    val selectedCategory: String = "Bhakti",
    val quotes: List<Quote> = emptyList(),
    val selectedQuote: Quote? = null,
    val selectedTemplate: Int = 1,
    val templates: List<TemplateItem> = defaultTemplates,
    val isLoading: Boolean = false
)

val defaultTemplates = listOf(
    TemplateItem(1, "Royal Purple", listOf(Color(0xFF8E2DE2), Color(0xFF4A00E0)), "👑"),
    TemplateItem(2, "Sunset Gold", listOf(Color(0xFFFDC830), Color(0xFFF37335)), "🌅"),
    TemplateItem(3, "Ocean Blue", listOf(Color(0xFF00c6ff), Color(0xFF0072ff)), "🌊"),
    TemplateItem(4, "Emerald", listOf(Color(0xFF1D976C), Color(0xFF93F9B9)), "🌿"),
    TemplateItem(5, "Rose Pink", listOf(Color(0xFFee9ca7), Color(0xFFffdde1)), "🌸"),
    TemplateItem(6, "Midnight", listOf(Color(0xFF232526), Color(0xFF414345)), "🌙"),
    TemplateItem(7, "Fire", listOf(Color(0xFFf12711), Color(0xFFf5af19)), "🔥"),
    TemplateItem(8, "Teal", listOf(Color(0xFF1a2980), Color(0xFF26d0ce)), "💎"),
)

@HiltViewModel
class StatusMakerViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(StatusMakerState())
    val state: StateFlow<StatusMakerState> = _state

    init {
        loadQuotesByCategory("Bhakti")
    }

    fun setStep(step: Int) {
        _state.value = _state.value.copy(step = step)
    }

    fun selectCategory(category: String) {
        _state.value = _state.value.copy(selectedCategory = category, step = 2, selectedTemplate = 0)
        loadQuotesByCategory(category)
    }

    fun selectQuote(quote: Quote) {
        _state.value = _state.value.copy(selectedQuote = quote)
    }

    fun selectTemplate(templateId: Int) {
        _state.value = _state.value.copy(selectedTemplate = templateId)
    }

    private fun loadQuotesByCategory(category: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            quoteRepository.getQuotes(category).collect { list ->
                _state.value = _state.value.copy(
                    quotes = list,
                    selectedQuote = list.firstOrNull(),
                    isLoading = false
                )
            }
        }
    }

    fun regenerate() {
        val currentQuotes = _state.value.quotes
        if (currentQuotes.isNotEmpty()) {
            _state.value = _state.value.copy(selectedQuote = currentQuotes.random())
        }
    }
}
