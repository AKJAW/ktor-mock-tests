package com.akjaw.ktor.mock.tests.domain

import com.akjaw.ktor.mock.tests.composition.appModule
import com.akjaw.ktor.mock.tests.domain.model.SearchResult
import com.akjaw.ktor.mock.tests.helper.runTest
import com.akjaw.ktor.mock.tests.helper.startApp
import com.akjaw.ktor.mock.tests.helper.stopApp
import com.akjaw.ktor.mock.tests.mock.GitHubApiMock
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

internal class InvalidKeywordSearchTest : KoinTest {

    private val gitHubApiMock: GitHubApiMock by inject()
    private val searchManager: SearchManager by inject()

    @BeforeEach
    fun setUp() {
        startApp()
    }

    @AfterEach
    fun tearDown() {
        stopApp()
    }

    @Test
    fun `Gives correct result if keyword is invalid`() = runTest {
        val result = searchManager.perform("te")

        result shouldBe SearchResult.InvalidKeyword
    }

    @Test
    fun `The API is not called if keyword is invalid`() = runTest {
        val result = searchManager.perform("te")

        result shouldBe SearchResult.InvalidKeyword

        gitHubApiMock.engine.requestHistory.shouldBeEmpty()
    }
}
