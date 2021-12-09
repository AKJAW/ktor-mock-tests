package com.akjaw.ktor.mock.tests.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akjaw.ktor.mock.tests.domain.SearchManager
import com.akjaw.ktor.mock.tests.domain.model.SearchResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchManager: SearchManager
) : ViewModel() {

    private val mutableResult = MutableStateFlow<SearchResult>(SearchResult.Success(emptyList()))
    val result: StateFlow<SearchResult> = mutableResult

    fun performSearch(input: String) = viewModelScope.launch {
        val result = searchManager.perform(input)

        mutableResult.value = result
    }
}
