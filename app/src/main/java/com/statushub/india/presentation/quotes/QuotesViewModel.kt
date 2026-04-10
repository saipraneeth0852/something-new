package com.statushub.india.presentation.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.statushub.india.data.local.Quote
import com.statushub.india.data.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val repository: QuoteRepository
) : ViewModel() {

    private val _quotes = MutableStateFlow<List<Quote>>(emptyList())
    val quotes: StateFlow<List<Quote>> = _quotes

    init {
        viewModelScope.launch {
            repository.prePopulateQuotesIfEmpty()
            loadQuotes("Motivation") // Default category
        }
    }

    private fun loadQuotes(category: String) {
        viewModelScope.launch {
            repository.getQuotes(category).collectLatest { list ->
                _quotes.value = list
            }
        }
    }
}
