package com.akjaw.ktor.mock.tests.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akjaw.ktor.mock.tests.ui.theme.KtorMockTestsTheme

@Composable
fun SearchScreen() {
    var searchKeyword by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    fun onSubmit() {
        Toast.makeText(context, "Searched for $searchKeyword", Toast.LENGTH_SHORT).show()
        searchKeyword = ""
    }

    val searchResult = if (searchKeyword.isEmpty()) {
        listOf()
    } else {
        previewResult.filter { it.contains(searchKeyword) }
    }

    SearchScreenContent(
        searchResult,
        searchKeyword,
        { searchKeyword = it },
        ::onSubmit
    )
}

@Composable
private fun SearchScreenContent(
    searchResults: List<String>,
    searchKeyword: String = "",
    onSearchKeywordChange: (String) -> Unit = {},
    onSubmit: () -> Unit = {}
) {
    Scaffold(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            SearchInput(
                value = searchKeyword,
                onValue = onSearchKeywordChange,
                onSubmit = { onSubmit() }
            )
            SearchResult(searchResults)
        }
    }
}

@Composable
private fun SearchInput(
    value: String,
    onValue: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        TextField(
            value = value,
            onValueChange = onValue,
            label = { Text(text = "Search for a repository") },
            maxLines = 1,
            singleLine = true,
            keyboardActions = KeyboardActions { onSubmit() },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        IconButton(
            onClick = {
                onSubmit()
            }
        ) {
            Icon(imageVector = Icons.Default.Send, contentDescription = "Send", tint = Color.Black)
        }
    }
}

@Composable
fun SearchResult(searchResult: List<String>) {
    LazyColumn(
        modifier = Modifier.padding(8.dp).fillMaxHeight(),
        verticalArrangement = spacedBy(6.dp)
    ) {
        searchResult.forEach { text ->
            item {
                Card {
                    Text(
                        text = text,
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmptySearchScreenPreview() {
    KtorMockTestsTheme {
        SearchScreenContent(searchResults = listOf())
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenWithResultsPreview() {
    KtorMockTestsTheme {
        SearchScreenContent(searchResults = previewResult)
    }
}

val previewResult = listOf("One", "Two", "Three", "Four")