package com.example.catfacts.modules.catlist.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.catfacts.R
import com.example.catfacts.data.domain.CatCategory
import com.example.catfacts.ui.theme.CatFactsAppTheme

@Composable
fun CategoryFilter(
    categories: List<CatCategory>,
    selectedCategory: CatCategory?,
    onCategorySelected: (CatCategory) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val dropDownTitle = selectedCategory?.name
        ?: stringResource(R.string.cat_list_screen_drop_down_label)

    Row(
        modifier = Modifier
            .clickable { expanded = true }
            .background(color = MaterialTheme.colors.secondary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(CategoryFilterDimens.spacerWidth))
        Box(
            modifier = Modifier
                .weight(CategoryFilterDimens.dropDownBoxWeight)
                .fillMaxHeight()
                .wrapContentSize(Alignment.TopStart)
        ) {
            Text(
                text = dropDownTitle,
                modifier = Modifier.fillMaxWidth()
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onCategorySelected(category)
                        }
                    ) {
                        Text(text = category.name)
                    }
                }
            }
        }
        Icon(
            modifier = Modifier.size(CategoryFilterDimens.dropDownIconSize),
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = stringResource(R.string.cat_list_screen_drop_down_label)
        )
    }
}

private object CategoryFilterDimens {
    val spacerWidth = 10.dp
    val dropDownIconSize = 40.dp
    const val dropDownBoxWeight = 1f
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun CategoryFilterPreview() {
    val categories = listOf(
        CatCategory(
            id = 0,
            name = "Example 1"
        ),
        CatCategory(
            id = 1,
            name = "Example 2"
        )
    )
    CatFactsAppTheme {
        LazyColumn {
            stickyHeader {
                CategoryFilter(
                    categories = categories,
                    selectedCategory = categories.first(),
                    onCategorySelected = {}
                )
            }
        }
    }
}
