package com.akjaw.ktor.mock.tests.helper

import com.akjaw.ktor.mock.tests.data.KtorHttpEngineHolder
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import org.koin.dsl.module

val mockEngineModule = module {
    single { GitHubApiMock() }
    single {
        val gitHubApiMock: GitHubApiMock = get()
        KtorHttpEngineHolder(gitHubApiMock.engine)
    }
}

class GitHubApiMock {

    val engine: HttpClientEngine = MockEngine { request ->
        respond(
            content = MockedApiResponse.FOR_TETRIS,
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }
}
