package com.akjaw.ktor.mock.tests.composition

import com.akjaw.ktor.mock.tests.data.GithubSearchApi
import com.akjaw.ktor.mock.tests.data.KtorHttpEngineHolder
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val appModule = module {
    single { KtorHttpEngineHolder() }
    single {
        val holder: KtorHttpEngineHolder = get()
        HttpClient(holder.engine) {
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
    }
    factory {
        GithubSearchApi(get())
    }
}
