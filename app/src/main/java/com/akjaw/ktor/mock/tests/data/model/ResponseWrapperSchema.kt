package com.akjaw.ktor.mock.tests.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseWrapperSchema(
    val items: List<RepositorySchema>
)
