package com.akjaw.ktor.mock.tests.domain

import com.akjaw.ktor.mock.tests.data.model.RepositorySchema
import com.akjaw.ktor.mock.tests.domain.model.RepositoryInfo

class RepositorySchemaConverter {

    fun convert(input: List<RepositorySchema>): List<RepositoryInfo> =
        input.map { schema ->
            RepositoryInfo(
                id = schema.id,
                name = schema.name,
                ownerName = schema.owner.name
            )
        }
}
