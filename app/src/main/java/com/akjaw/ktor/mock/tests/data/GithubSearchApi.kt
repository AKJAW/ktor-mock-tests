package com.akjaw.ktor.mock.tests.data

import com.akjaw.ktor.mock.tests.data.model.RepositorySchema
import com.akjaw.ktor.mock.tests.data.model.ResponseWrapperSchema
import io.ktor.client.HttpClient
import io.ktor.client.features.ResponseException
import io.ktor.client.request.get

class GithubSearchApi(
    private val httpClient: HttpClient
) {

    suspend fun fetchRepositories(searchKeyword: String): List<RepositorySchema>? {
        return try {
            val wrapper: ResponseWrapperSchema = httpClient
                .get("https://api.github.com/search/repositories?q=$searchKeyword&sort=stars&order=desc")
            wrapper.items
        } catch (e: ResponseException) {
            null
        }
    }
}
