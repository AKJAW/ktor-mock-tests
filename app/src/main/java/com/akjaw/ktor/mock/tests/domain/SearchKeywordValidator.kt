package com.akjaw.ktor.mock.tests.domain

class SearchKeywordValidator {

    fun validate(input: String): Boolean = input.trim().count() in 3..20
}
