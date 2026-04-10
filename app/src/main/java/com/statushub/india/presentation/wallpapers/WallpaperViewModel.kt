package com.statushub.india.presentation.wallpapers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.statushub.india.data.remote.PexelsPhoto
import com.statushub.india.data.repository.WallpaperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WallpaperUiState(
    val wallpapers: List<PexelsPhoto> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val selectedCategory: String = "nature",
    val currentPage: Int = 1,
    val hasMore: Boolean = true
)

@HiltViewModel
class WallpaperViewModel @Inject constructor(
    private val repository: WallpaperRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WallpaperUiState())
    val uiState: StateFlow<WallpaperUiState> = _uiState.asStateFlow()

    // Legacy delegating properties for backwards compatibility with existing screens
    val wallpapers: StateFlow<List<PexelsPhoto>> = MutableStateFlow(emptyList())
    val isLoading: StateFlow<Boolean> = MutableStateFlow(false)
    val selectedCategory: StateFlow<String> = MutableStateFlow("nature")

    private val perPage = 30

    init {
        fetchWallpapers("nature")
    }

    fun fetchWallpapers(category: String, page: Int = 1) {
        if (page == 1) {
            _uiState.value = _uiState.value.copy(
                selectedCategory = category,
                isLoading = true,
                error = null,
                wallpapers = emptyList(),
                currentPage = 1,
                hasMore = true
            )
        } else {
            _uiState.value = _uiState.value.copy(isLoadingMore = true, error = null)
        }

        viewModelScope.launch {
            repository.getWallpapers(category, page, perPage)
                .onSuccess { photos ->
                    val currentWallpapers = _uiState.value.wallpapers.toMutableList()
                    if (page == 1) currentWallpapers.clear()
                    currentWallpapers.addAll(photos)

                    _uiState.value = _uiState.value.copy(
                        wallpapers = currentWallpapers,
                        isLoading = false,
                        isLoadingMore = false,
                        currentPage = page,
                        hasMore = photos.size >= perPage,
                        error = null
                    )
                }
                .onFailure { throwable ->
                    val errorMessage = throwable.message ?: "Unknown error occurred"
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        error = if (page == 1) errorMessage else null,
                        wallpapers = if (page == 1) emptyList() else _uiState.value.wallpapers
                    )
                }
        }
    }

    fun loadMore() {
        val currentState = _uiState.value
        if (!currentState.isLoadingMore && currentState.hasMore && !currentState.isLoading) {
            fetchWallpapers(currentState.selectedCategory, currentState.currentPage + 1)
        }
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
