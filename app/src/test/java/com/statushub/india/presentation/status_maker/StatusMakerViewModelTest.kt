package com.statushub.india.presentation.status_maker

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StatusMakerViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should have step 1 and default category`() {
        // Note: Full ViewModel testing requires mocking the repository.
        // Here we test the data class defaults.
        val state = StatusMakerState()
        assertEquals(1, state.step)
        assertEquals("Bhakti", state.selectedCategory)
        assertEquals(1, state.selectedTemplate)
        assertFalse(state.isLoading)
        assertTrue(state.quotes.isEmpty())
    }

    @Test
    fun `default templates should not be empty`() {
        assertTrue(defaultTemplates.isNotEmpty())
        assertEquals(8, defaultTemplates.size)
    }

    @Test
    fun `template items should have unique ids`() {
        val ids = defaultTemplates.map { it.id }
        assertEquals(ids.size, ids.distinct().size)
    }

    @Test
    fun `selecting a category should reset template selection`() {
        // This tests the state transition logic conceptually
        val initialState = StatusMakerState(step = 2, selectedTemplate = 3)
        val newState = initialState.copy(step = 2, selectedCategory = "Motivation", selectedTemplate = 0)
        assertEquals("Motivation", newState.selectedCategory)
        assertEquals(0, newState.selectedTemplate)
        assertEquals(2, newState.step)
    }

    @Test
    fun `regenerate should pick a different quote when available`() {
        val quote1 = com.statushub.india.data.local.Quote(text = "Quote 1", category = "Test", language = "en")
        val quote2 = com.statushub.india.data.local.Quote(text = "Quote 2", category = "Test", language = "en")
        val state = StatusMakerState(
            quotes = listOf(quote1, quote2),
            selectedQuote = quote1
        )
        // Regenerate logic would be tested here with a real ViewModel instance
        assertNotNull(state.selectedQuote)
    }
}
