package com.akjaw.ktor.mock.tests.data

import com.akjaw.ktor.mock.tests.composition.appModule
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class CorrectlyFetchesRepositories : KoinTest {

    private val githubSearchApi: GithubSearchApi by inject()

    @BeforeEach
    fun setUp() {
        startKoin {
            modules(appModule)
        }
    }

    @AfterEach
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `Returns the correct first repository for 'tetris' keyword`(): Unit = runBlocking {
        val result = githubSearchApi.fetchRepositories("tetris")

        val firstRepository = result.first()
        assertSoftly(firstRepository) {
            id shouldBe 76954504
            name shouldBe "react-tetris"
            owner.name shouldBe "chvin"
        }
    }
}
