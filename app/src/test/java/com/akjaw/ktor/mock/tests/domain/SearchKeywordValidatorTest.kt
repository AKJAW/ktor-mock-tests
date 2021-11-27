package com.akjaw.ktor.mock.tests.domain

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

internal class SearchKeywordValidatorTest {

    private lateinit var systemUnderTest: SearchKeywordValidator

    @BeforeEach
    fun setUp() {
        systemUnderTest = SearchKeywordValidator()
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(SearchKeywordInputArgumentsProvider::class)
    fun `Correctly validates the keyword`(name: String, input: String, expectedResult: Boolean) {
        val result = systemUnderTest.validate(input)

        result shouldBe expectedResult
    }

    class SearchKeywordInputArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                testCase("Empty input is invalid", "", false),
                testCase("Two char input is invalid", "ca", false),
                testCase("Three char input is valid", "car", true),
                testCase("Three char input with whitespace is invalid", " ca", false),
                testCase("Twenty char input is valid", "c".repeat(20), true),
                testCase("Twenty one char input is invalid", "c".repeat(21), false),
            )
        }

        private fun testCase(name: String, input: String, expectedResult: Boolean) =
            Arguments.of(name, input, expectedResult)
    }
}
