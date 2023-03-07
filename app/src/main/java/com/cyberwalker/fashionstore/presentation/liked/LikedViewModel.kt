package com.cyberwalker.fashionstore.presentation.liked

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cyberwalker.fashionstore.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LikedViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    var uiState by mutableStateOf(LikedUiState())
        private set

}
data class LikedUiState(
    val favoriteItem: List<Int> = emptyList(),
    val loadComplete: Boolean = false
)
