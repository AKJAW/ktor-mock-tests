package com.akjaw.ktor.mock.tests.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akjaw.ktor.mock.tests.R
import com.akjaw.ktor.mock.tests.domain.model.RepositoryInfo
import com.akjaw.ktor.mock.tests.domain.model.SearchResult
import com.akjaw.ktor.mock.tests.presentation.SearchViewModel
import com.akjaw.ktor.mock.tests.ui.theme.KtorMockTestsTheme
import org.koin.androidx.compose.get

object TestTagsSearchScreen {
    const val SEARCH_RESULT = "SEARCH_RESULT"
}

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = get()
) {
    var searchKeyword by remember {
        mutableStateOf("")
    }

    val results = searchViewModel.result.collectAsState()

    fun onSubmit() {
        searchViewModel.performSearch(searchKeyword)
    }

    SearchScreenContent(
        results.value,
        searchKeyword,
        {
            searchKeyword = it
        },
        ::onSubmit
    )
}

@Composable
private fun SearchScreenContent(
    searchResults: SearchResult,
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
            Box(Modifier.testTag(TestTagsSearchScreen.SEARCH_RESULT)) {
                when (searchResults) {
                    is SearchResult.Success -> {
                        SearchResultColumn(searchResults.repositories)
                    }
                    SearchResult.ApiError -> {
                        Text(text = stringResource(id = R.string.api_error))
                    }
                    SearchResult.InvalidKeyword -> {
                        Text(text = stringResource(id = R.string.input_too_short))
                    }
                }
            }
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
            label = { Text(text = stringResource(id = R.string.input_label)) },
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
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = stringResource(id = R.string.send),
                tint = Color.Black
            )
        }
    }
}

@Composable
private fun SearchResultColumn(repositories: List<RepositoryInfo>) {
    if (repositories.isEmpty()) {
        Text(text = stringResource(id = R.string.empty_results))
    } else {
        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxHeight(),
            verticalArrangement = spacedBy(6.dp)
        ) {
            items(
                items = repositories,
                key = { it.id }
            ) { repository ->
                Card {
                    Text(
                        text = repository.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptySearchScreenPreview() {
    KtorMockTestsTheme {
        SearchScreenContent(searchResults = SearchResult.Success(emptyList()))
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenWithResultsPreview() {
    KtorMockTestsTheme {
        SearchScreenContent(searchResults = previewResult)
    }
}

val previewResult = SearchResult.Success(
    listOf("One", "Two", "Three", "Four").map { name ->
        RepositoryInfo(0, name, "")
    }
)