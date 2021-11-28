package com.akjaw.ktor.mock.tests.data

import com.akjaw.ktor.mock.tests.data.model.RepositorySchema
import com.akjaw.ktor.mock.tests.data.model.ResponseWrapperSchema
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import kotlinx.serialization.json.Json

class GithubSearchApi {

    private val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    suspend fun fetchRepositories(searchKeyword: String): List<RepositorySchema> {
        val wrapper: ResponseWrapperSchema = httpClient
            .get("https://api.github.com/search/repositories?q=$searchKeyword&sort=stars&order=desc")
        return wrapper.items
    }
}

