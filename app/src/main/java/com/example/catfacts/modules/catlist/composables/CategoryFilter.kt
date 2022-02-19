package com.example.catfacts.modules.catlist.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.catfacts.R
import com.example.catfacts.data.domain.CatCategory

@Composable
fun CategoryFilter(
    categories: List<CatCategory>,
    selectedCategory: CatCategory?,
    onCategorySelected: (CatCategory) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val dropDownTitle = selectedCategory?.let { category ->
        category.name
    } ?: stringResource(R.string.cat_list_screen_drop_down_label)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
    ) {
        Text(
            text = dropDownTitle,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
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
}
