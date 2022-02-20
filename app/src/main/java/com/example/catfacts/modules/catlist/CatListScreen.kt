package com.example.catfacts.modules.catlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.catfacts.R
import com.example.catfacts.data.domain.CatCategory
import com.example.catfacts.modules.catlist.composables.CategoryFilter
import com.example.catfacts.modules.catlist.model.CatListCategoriesData
import com.example.catfacts.modules.catlist.model.CatListData
import com.example.catfacts.ui.composables.ErrorRetryView
import com.example.catfacts.ui.composables.LoadingIndicator
import com.example.catfacts.ui.composables.SingleAction
import com.example.catfacts.ui.model.LoadingState
import com.example.catfacts.ui.model.State
import org.koin.androidx.compose.viewModel

@Composable
fun CatListScreen() {
    val vm by viewModel<CatListViewModel>()
    val categoriesState by vm.categoriesState.collectAsState(initial = null)
    val listState by vm.listState.collectAsState(initial = null)

    SingleAction {
        vm.loadCategories()
    }

    CatListScreen(
        categoriesState = categoriesState,
        loadCategoriesAction = vm::loadCategories,
        onCategorySelected = vm::onCategorySelected,
        listState = listState,
        reloadListAction = vm::reloadList
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatListScreen(
    categoriesState: State<CatListCategoriesData>?,
    loadCategoriesAction: () -> Unit,
    onCategorySelected: (CatCategory) -> Unit,
    listState: State<CatListData>?,
    reloadListAction: () -> Unit
) {
    if (categoriesState == null) {
        return
    }

    val isLoading = categoriesState.loadingState == LoadingState.LOADING ||
            listState?.loadingState == LoadingState.LOADING
    val errorWhileLoadingCategories = categoriesState.loadingState == LoadingState.ERROR ||
            categoriesState.data?.categories.isNullOrEmpty()
    val errorWhileLoadingList = listState?.loadingState == LoadingState.ERROR

    when {
        isLoading -> {
            LoadingIndicator()
        }
        errorWhileLoadingCategories -> {
            ErrorRetryView(errorText = stringResource(R.string.error_generic)) {
                loadCategoriesAction()
            }
        }
        errorWhileLoadingList -> {
            ErrorRetryView(errorText = stringResource(R.string.error_generic)) {
                reloadListAction()
            }
        }
        categoriesState.loadingState == LoadingState.SUCCESS -> {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                stickyHeader {
                    CategoryFilter(
                        categories = categoriesState.data?.categories ?: emptyList(),
                        onCategorySelected = onCategorySelected,
                        selectedCategory = categoriesState.data?.selectedCategory
                    )
                }
                if (categoriesState.data?.selectedCategory != null) {
                    listState?.data?.let { listData ->
                        items(items = listData.entries) { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(CatListScreenDimens.imageRowHeight),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.FillHeight,
                                    painter = rememberImagePainter(
                                        item.imageUrl,
                                        builder = {
                                            placeholder(R.drawable.ic_cat_image_placeholder)
                                        }
                                    ),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private object CatListScreenDimens {
    val imageRowHeight = 300.dp
}
