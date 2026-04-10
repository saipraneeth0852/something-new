package com.statushub.india.presentation.wallpapers

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class WallpaperViewModelTest {

    @Test
    fun `initial UiState should have default values`() {
        val state = WallpaperUiState()
        assertTrue(state.wallpapers.isEmpty())
        assertFalse(state.isLoading)
        assertFalse(state.isLoadingMore)
        assertEquals(null, state.error)
        assertEquals("nature", state.selectedCategory)
        assertEquals(1, state.currentPage)
        assertTrue(state.hasMore)
    }

    @Test
    fun `UiState copy with error should preserve wallpapers`() {
        val initialState = WallpaperUiState(
            wallpapers = listOf(),
            selectedCategory = "god"
        )
        val newState = initialState.copy(error = "Network error")
        assertEquals("Network error", newState.error)
        assertEquals(initialState.wallpapers, newState.wallpapers)
        assertEquals("god", newState.selectedCategory)
    }

    @Test
    fun `UiState loading more should not clear existing wallpapers`() {
        val photo = com.statushub.india.data.remote.PexelsPhoto(
            id = 1, width = 100, height = 100, url = "", 
            src = com.statushub.india.data.remote.PexelsPhotoSource("", "", "", "", "", "", ""),
            alt = ""
        )
        val initialState = WallpaperUiState(
            wallpapers = listOf(photo),
            currentPage = 1
        )
        val newState = initialState.copy(isLoadingMore = true, error = null)
        assertTrue(newState.isLoadingMore)
        assertEquals(1, newState.wallpapers.size)
    }
}
