package com.akjaw.ktor.mock.tests

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.akjaw.ktor.mock.tests.ui.SearchScreen
import com.akjaw.ktor.mock.tests.ui.theme.KtorMockTestsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KtorMockTestsTheme {
                // A surface container using the 'background' color from the theme
                SearchScreen()
            }
        }
    }
}
