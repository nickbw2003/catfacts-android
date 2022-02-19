package com.example.catfacts.modules.catlist.model

import com.example.catfacts.data.domain.CatCategory

data class CatListCategoriesData(
    val categories: List<CatCategory>,
    val selectedCategory: CatCategory? = null
)
