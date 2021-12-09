package com.akjaw.ktor.mock.tests

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.akjaw.ktor.mock.tests.composition.appModule
import com.akjaw.ktor.mock.tests.mock.GitHubApiMock
import com.akjaw.ktor.mock.tests.mock.mockEngineModule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.test.KoinTest
import org.koin.test.inject

class SearchScreenTest : KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val gitHubApiMock: GitHubApiMock by inject()

    @Before
    fun setUp() {
        loadKoinModules(listOf(appModule, mockEngineModule))
        Log.d("CoolTag", "setUp $gitHubApiMock")
    }

    @After
    fun tearDown() {
        unloadKoinModules(listOf(appModule, mockEngineModule))
    }

    @Test
    fun initiallyThereAreNoResults() {
        composeTestRule
            .onNodeWithText(composeTestRule.getString(R.string.empty_results))
            .assertIsDisplayed()
    }

    @Test
    fun invalidInputShowsErrorMessage() {
        gitHubApiMock.givenSuccess()

        composeTestRule.searchFor("te")

        composeTestRule
            .onNodeWithText(composeTestRule.getString(R.string.input_too_short))
            .assertIsDisplayed()
    }

    @Test
    fun inputWithoutResultsShowsNoResults() {
        gitHubApiMock.givenSuccess()

        composeTestRule.searchFor("asdasdasdas")

        composeTestRule
            .onNodeWithText(composeTestRule.getString(R.string.empty_results))
            .assertIsDisplayed()
    }

    @Test
    fun correctInputGivesTheCorrectFirstResult() {
        gitHubApiMock.givenSuccess()

        composeTestRule.searchFor("tet")

        composeTestRule.waitUntilAssertion {
            composeTestRule
                .onNodeWithText("tether")
                .assertIsDisplayed()
        }
    }

    @Test
    fun apiErrorIsDisplayed() {
        gitHubApiMock.givenFailure()

        composeTestRule.searchFor("asda")

        composeTestRule.waitUntilAssertion(5000) {
            composeTestRule
                .onNodeWithText(composeTestRule.getString(R.string.api_error))
                .assertIsDisplayed()
        }
    }

    private fun ComposeTestRule.searchFor(input: String) {
        onNodeWithText(composeTestRule.getString(id = R.string.input_label))
            .performTextInput(input)

        onNodeWithContentDescription(composeTestRule.getString(id = R.string.send))
            .performClick()
    }

    private fun AndroidComposeTestRule<*, *>.getString(@StringRes id: Int) =
        this.activity.getString(id)

    private fun ComposeTestRule.waitUntilAssertion(
        timeoutMillis: Long = 1_000,
        assertion: () -> Unit
    ) {
        waitUntil(timeoutMillis) {
            try {
                assertion()
                true
            } catch (e: Throwable) {
                false
            }
        }
    }
}
