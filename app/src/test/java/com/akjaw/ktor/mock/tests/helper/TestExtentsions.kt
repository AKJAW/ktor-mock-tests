package com.akjaw.ktor.mock.tests.helper

import com.akjaw.ktor.mock.tests.composition.appModule
import com.akjaw.ktor.mock.tests.mock.mockEngineModule
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

fun startApp() {
    startKoin {
        modules(appModule, mockEngineModule)
    }
}

fun stopApp() {
    stopKoin()
}

fun runTest(block: suspend () -> Unit): Unit = runBlocking { block() }
