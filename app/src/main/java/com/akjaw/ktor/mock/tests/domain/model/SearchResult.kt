package com.akjaw.ktor.mock.tests.domain.model

sealed class SearchResult {
    object InvalidKeyword : SearchResult()
}
