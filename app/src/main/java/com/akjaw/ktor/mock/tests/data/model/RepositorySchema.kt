package com.akjaw.ktor.mock.tests.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RepositorySchema(
    val id: Int,
    val name: String,
    val owner: OwnerSchema
)
