package com.akjaw.ktor.mock.tests.domain.model

sealed class SearchResult {
    data class Success(val repositories: List<RepositoryInfo>) : SearchResult()
    object InvalidKeyword : SearchResult()
}
