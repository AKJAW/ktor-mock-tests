package com.akjaw.ktor.mock.tests.composition

import com.akjaw.ktor.mock.tests.data.GithubSearchApi
import org.koin.dsl.module

val appModule = module {
    factory { GithubSearchApi() }
}
