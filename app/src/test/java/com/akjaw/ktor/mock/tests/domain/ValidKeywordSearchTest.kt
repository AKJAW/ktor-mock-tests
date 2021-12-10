package com.akjaw.ktor.mock.tests.domain

import com.akjaw.ktor.mock.tests.domain.model.RepositoryInfo
import com.akjaw.ktor.mock.tests.domain.model.SearchResult
import com.akjaw.ktor.mock.tests.helper.runTest
import com.akjaw.ktor.mock.tests.helper.startApp
import com.akjaw.ktor.mock.tests.helper.stopApp
import com.akjaw.ktor.mock.tests.mock.GitHubApiMock
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class ValidKeywordSearchTest : KoinTest {

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
    fun `On API success returns the correct first result for keyword 'tetris'`() = runTest {
        gitHubApiMock.givenSuccess()

        val result = searchManager.perform(GitHubApiMock.TETRIS_KEYWORD)

        assertSoftly {
            result.shouldBeInstanceOf<SearchResult.Success>()
            result.repositories.first() shouldBe RepositoryInfo(
                id = 76954504,
                name = "react-tetris",
                ownerName = "chvin"
            )
        }
    }

    @Test
    fun `On API failure returns an error`() = runTest {
        gitHubApiMock.givenFailure()

        val result = searchManager.perform(GitHubApiMock.TETRIS_KEYWORD)

        result shouldBe SearchResult.ApiError
    }
}
