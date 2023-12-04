package com.latihan.batiqu.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.latihan.batiqu.data.local.Batik
import com.latihan.batiqu.data.repository.BatikRepository
import com.latihan.batiqu.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val batikRepository: BatikRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Batik>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Batik>>>
        get() = _uiState

    fun getFavoriteBatik() = viewModelScope.launch {
        batikRepository.getFavoriteBatik()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateBatik(id: Int, newState: Boolean) {
        batikRepository.updateBatik(id, newState)
        getFavoriteBatik()
    }
}