package com.akjaw.ktor.mock.tests.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerSchema(
    @SerialName("login")
    val name: String
)
