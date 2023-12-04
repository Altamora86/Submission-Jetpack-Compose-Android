package com.latihan.batiqu.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.latihan.batiqu.data.local.Batik
import com.latihan.batiqu.data.repository.BatikRepository
import com.latihan.batiqu.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val batikRepository: BatikRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Batik>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Batik>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        batikRepository.searchBatik(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateBatik(id: Int, newState: Boolean) = viewModelScope.launch {
        batikRepository.updateBatik(id, newState)
            .collect { isUpdated ->
                if (isUpdated) search(_query.value)
            }
    }
}