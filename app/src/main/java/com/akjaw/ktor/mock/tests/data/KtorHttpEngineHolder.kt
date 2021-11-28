package com.akjaw.ktor.mock.tests.data

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO

class KtorHttpEngineHolder(
    val engine: HttpClientEngine = CIO.create()
)
