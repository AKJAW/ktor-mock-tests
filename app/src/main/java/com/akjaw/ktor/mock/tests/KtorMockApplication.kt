package com.akjaw.ktor.mock.tests

import android.app.Application
import com.akjaw.ktor.mock.tests.composition.appModule
import com.akjaw.ktor.mock.tests.mock.GitHubApiMock
import com.akjaw.ktor.mock.tests.mock.mockEngineModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class KtorMockApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule)
        }
    }
}
