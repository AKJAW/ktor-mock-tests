package com.akjaw.ktor.mock.tests.domain

import com.akjaw.ktor.mock.tests.data.GithubSearchApi
import com.akjaw.ktor.mock.tests.domain.model.SearchResult

class SearchManager(
    private val keywordValidator: SearchKeywordValidator,
    private val githubSearchApi: GithubSearchApi,
    private val repositorySchemaConverter: RepositorySchemaConverter,
) {

    suspend fun perform(keyword: String): SearchResult {
        if (keywordValidator.validate(keyword).not()) return SearchResult.InvalidKeyword
        val repositorySchemas = githubSearchApi.fetchRepositories(keyword)!!
        val repositories = repositorySchemaConverter.convert(repositorySchemas)
        return SearchResult.Success(repositories)
    }
}
