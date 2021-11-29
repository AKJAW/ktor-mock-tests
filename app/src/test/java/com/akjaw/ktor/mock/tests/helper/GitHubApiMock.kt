package com.akjaw.ktor.mock.tests.helper

import com.akjaw.ktor.mock.tests.data.KtorHttpEngineHolder
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
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

    companion object {
        const val TETRIS_KEYWORD = "tetris"
        const val TET_KEYWORD = "tet"
    }

    private var isSuccess: Boolean? = null
        get() = field ?: throw IllegalStateException("Mock has not beet initialized")

    fun givenSuccess() {
        isSuccess = true
    }

    fun givenFailure() {
        isSuccess = false
    }

    val engine = MockEngine { request ->

        handleSearchRequest(request)
            ?: errorResponse()
    }

    private fun MockRequestHandleScope.handleSearchRequest(request: HttpRequestData): HttpResponseData? {
        if (request.url.encodedPath.contains("search/repositories").not()) {
            return null
        }

        val searchKeyword = request.url.parameters["q"] ?: ""
        val responseContent = when (searchKeyword.lowercase()) {
            TETRIS_KEYWORD -> MockedApiResponse.FOR_TETRIS
            TET_KEYWORD -> MockedApiResponse.FOR_TET
            else -> MockedApiResponse.FOR_INVALID
        }

        val statusCode = if (isSuccess == true) {
            HttpStatusCode.OK
        } else {
            HttpStatusCode.InternalServerError
        }

        return respond(
            content = responseContent,
            status = statusCode,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }

    private fun MockRequestHandleScope.errorResponse(): HttpResponseData {
        return respond(
            content = "",
            status = HttpStatusCode.BadRequest,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )

    }
}
