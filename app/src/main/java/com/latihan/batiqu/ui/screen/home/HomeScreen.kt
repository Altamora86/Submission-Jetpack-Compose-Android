package com.latihan.batiqu.ui.screen.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.latihan.batiqu.R
import com.latihan.batiqu.ViewModelFactory
import com.latihan.batiqu.data.local.Batik
import com.latihan.batiqu.di.Injection
import com.latihan.batiqu.ui.components.EmptyList
import com.latihan.batiqu.ui.components.BatikItem
import com.latihan.batiqu.ui.components.SearchButton
import com.latihan.batiqu.ui.theme.BatiquTheme
import com.latihan.batiqu.utils.UiState


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.search(query)
            }

            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::search,
                    listBatik = uiState.data,
                    onFavoriteIconClicked = { id, newState ->
                        viewModel.updateBatik(id, newState)
                    },
                    navigateToDetail = navigateToDetail
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    listBatik: List<Batik>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    navigateToDetail: (Int) -> Unit,
) {
    Column {
        SearchButton(
            query = query,
            onQueryChange = onQueryChange
        )
        if (listBatik.isNotEmpty()) {
            ListBatik(
                listBatik = listBatik,
                onFavoriteIconClicked = onFavoriteIconClicked,
                navigateToDetail = navigateToDetail
            )
        } else {
            EmptyList(
                warning = stringResource(R.string.empty_data),
                modifier = Modifier
                    .testTag("emptyList")
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListBatik(
    listBatik: List<Batik>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    contentPaddingTop: Dp = 0.dp,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
            top = contentPaddingTop
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .testTag("lazy_list")
    ) {
        items(listBatik, key = { it.id }) { item ->
            Card(
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                modifier = Modifier.padding(vertical = 0.dp, horizontal = 8.dp)
            ) {
                BatikItem(
                    id = item.id,
                    name = item.name,
                    origin = item.origin,
                    image = item.image,
                    price = item.price,
                    isFavorite = item.isFavorite,
                    onFavoriteIconClicked = onFavoriteIconClicked,
                    modifier = Modifier
                        .animateItemPlacement(tween(durationMillis = 200))
                        .clickable { navigateToDetail(item.id) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BatiquAppPreview() {
    BatiquTheme {
        val navigateToDetail: (Int) -> Unit = {}
        HomeScreen(navigateToDetail = navigateToDetail)
    }
}