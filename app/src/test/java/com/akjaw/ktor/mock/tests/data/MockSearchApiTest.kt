package com.akjaw.ktor.mock.tests.data

import com.akjaw.ktor.mock.tests.composition.appModule
import com.akjaw.ktor.mock.tests.helper.GitHubApiMock
import com.akjaw.ktor.mock.tests.helper.mockEngineModule
import com.akjaw.ktor.mock.tests.helper.runTest
import com.akjaw.ktor.mock.tests.helper.startApp
import com.akjaw.ktor.mock.tests.helper.stopApp
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class MockSearchApiTest : KoinTest {

    private val githubSearchApi: GithubSearchApi by inject()
    private val gitHubApiMock: GitHubApiMock by inject()

    @BeforeEach
    fun setUp() {
        startApp()
    }

    @AfterEach
    fun tearDown() {
        stopApp()
    }

    @Test
    fun `Returns the correct first repository for 'tetris' keyword`(): Unit = runTest {
        gitHubApiMock.givenSuccess()

        val result = githubSearchApi.fetchRepositories(GitHubApiMock.TETRIS_KEYWORD)

        val firstRepository = result?.first()
        assertSoftly(firstRepository) {
            this?.id shouldBe 76954504
            this?.name shouldBe "react-tetris"
            this?.owner?.name shouldBe "chvin"
        }
    }

    @Test
    fun `Returns the correct first repository for 'tet' keyword`(): Unit = runTest {
        gitHubApiMock.givenSuccess()

        val result = githubSearchApi.fetchRepositories(GitHubApiMock.TET_KEYWORD)

        val firstRepository = result?.first()
        assertSoftly(firstRepository) {
            this?.id shouldBe 13597334
            this?.name shouldBe "tether"
            this?.owner?.name shouldBe "shipshapecode"
        }
    }

    @Test
    fun `Returns empty list on invalid keyword`(): Unit = runTest {
        gitHubApiMock.givenSuccess()

        val result = githubSearchApi.fetchRepositories("")

        result.shouldBeEmpty()
    }

    @Test
    fun `Returns null on error`(): Unit = runBlocking {
        gitHubApiMock.givenFailure()

        val result = githubSearchApi.fetchRepositories("")

        result.shouldBeNull()
    }
}
