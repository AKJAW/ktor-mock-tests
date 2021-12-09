package com.akjaw.ktor.mock.tests.domain

import com.akjaw.ktor.mock.tests.data.GithubSearchApi
import com.akjaw.ktor.mock.tests.domain.model.SearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchManager(
    private val keywordValidator: SearchKeywordValidator,
    private val githubSearchApi: GithubSearchApi,
    private val repositorySchemaConverter: RepositorySchemaConverter,
) {

    suspend fun perform(keyword: String): SearchResult = withContext(Dispatchers.IO) {
        if (keywordValidator.validate(keyword).not()) return@withContext SearchResult.InvalidKeyword

        val repositorySchemas = githubSearchApi.fetchRepositories(keyword)
            ?: return@withContext SearchResult.ApiError

        val repositories = repositorySchemaConverter.convert(repositorySchemas)
        return@withContext SearchResult.Success(repositories)
    }
}
