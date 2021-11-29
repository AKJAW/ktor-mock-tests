package com.akjaw.ktor.mock.tests.domain

import com.akjaw.ktor.mock.tests.domain.model.SearchResult

class SearchManager {

    fun perform(keyword: String): SearchResult {
        return SearchResult.InvalidKeyword
    }
}
