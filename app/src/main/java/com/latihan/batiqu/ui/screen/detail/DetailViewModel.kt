package com.latihan.batiqu.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.latihan.batiqu.data.local.Batik
import com.latihan.batiqu.data.repository.BatikRepository
import com.latihan.batiqu.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val batikRepository: BatikRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Batik>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Batik>>
        get() = _uiState

    fun getBatikById(id: Int) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(batikRepository.getBatikById(id))
    }


    fun updateBatik(id: Int, newState: Boolean) = viewModelScope.launch {
        batikRepository.updateBatik(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getBatikById(id)
            }
    }
}
