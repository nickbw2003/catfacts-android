package com.example.catfacts.modules.catlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catfacts.data.catimage.CatImageRepository
import com.example.catfacts.data.domain.CatCategory
import com.example.catfacts.data.domain.Response
import com.example.catfacts.modules.catlist.model.CatListCategoriesData
import com.example.catfacts.modules.catlist.model.CatListData
import com.example.catfacts.modules.catlist.model.CatListEntry
import com.example.catfacts.ui.model.LoadingState
import com.example.catfacts.ui.model.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatListViewModel(
    private val catImageRepository: CatImageRepository
) : ViewModel() {

    private val _categoriesState = MutableStateFlow<State<CatListCategoriesData>?>(null)
    val categoriesState: Flow<State<CatListCategoriesData>?>
        get() = _categoriesState

    private val _listState = MutableStateFlow<State<CatListData>?>(null)
    val listState: Flow<State<CatListData>?>
        get() = _listState

    fun loadCategories() = viewModelScope.launch {
        _categoriesState.value = State(loadingState = LoadingState.LOADING)
        withContext(Dispatchers.IO) {
            catImageRepository.getImageCategories()
                .mapToState()
                .collect { state ->
                    _categoriesState.value = state
                }
        }
    }

    fun onCategorySelected(category: CatCategory) = viewModelScope.launch {
        _categoriesState.value = State(
            data = CatListCategoriesData(
                categories = _categoriesState.value?.data?.categories ?: emptyList(),
                selectedCategory = category
            ),
            loadingState = LoadingState.SUCCESS
        )
        _listState.value = State(loadingState = LoadingState.LOADING)

        withContext(Dispatchers.IO) {
            catImageRepository
                .getCatImages(
                    categoryId = category.id,
                    limit = catListSize
                ).collect { catImagesResponse ->
                    val state = if (catImagesResponse is Response.Success) {
                        val entries = catImagesResponse.data.map { image ->
                            CatListEntry(
                                imageUrl = image.url
                            )
                        }
                        State(
                            data = CatListData(
                                entries = entries
                            ),
                            loadingState = LoadingState.SUCCESS
                        )
                    } else {
                        State(
                            loadingState = LoadingState.ERROR
                        )
                    }
                    _listState.value = state
                }
        }
    }

    private fun Flow<Response<List<CatCategory>>>.mapToState(): Flow<State<CatListCategoriesData>?> =
        map { response ->
            if (response is Response.Success && response.data.isNotEmpty()) {
                val categories = response.data
                State(
                    data = CatListCategoriesData(
                        categories = categories
                    ),
                    loadingState = LoadingState.SUCCESS
                )
            } else {
                State(
                    loadingState = LoadingState.ERROR
                )
            }
        }

    companion object {
        private const val catListSize = 10
    }
}
