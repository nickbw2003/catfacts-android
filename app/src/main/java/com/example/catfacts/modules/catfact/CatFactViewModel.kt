package com.example.catfacts.modules.catfact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catfacts.data.LocalImageHandler
import com.example.catfacts.data.RemoteImageHandler
import com.example.catfacts.data.catfact.CatFactRepository
import com.example.catfacts.data.catimage.CatImageRepository
import com.example.catfacts.data.domain.CatFact
import com.example.catfacts.data.domain.CatImage
import com.example.catfacts.data.domain.Response
import com.example.catfacts.modules.catfact.model.CatFactData
import com.example.catfacts.ui.model.LoadingState
import com.example.catfacts.ui.model.State
import com.example.catfacts.ui.navigation.TopBarAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatFactViewModel(
    private val catImageRepository: CatImageRepository,
    private val catFactRepository: CatFactRepository,
    private val remoteImageHandler: RemoteImageHandler,
    private val localImageHandler: LocalImageHandler
) : ViewModel() {

    private val _state = MutableStateFlow<State<CatFactData>?>(null)
    val state: Flow<State<CatFactData>?>
        get() = _state

    fun load() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            _state.value = State(loadingState = LoadingState.LOADING)
            combineRandomImageAndFact()
                .mapToState()
                .collect { state ->
                    _state.value = state
                }
        }
    }

    fun onTopBarActionClicked(topBarAction: TopBarAction?) {
        when (topBarAction) {
            is TopBarAction.Share -> {
                // TODO
            }
            is TopBarAction.SaveToGallery -> {
                saveLatestDownloadedImageToGallery()
            }
            null -> {
                // nothing to do here
            }
        }
    }

    private fun combineRandomImageAndFact() = catImageRepository.getRandomCatImage()
        .combine(catFactRepository.getRandomCatFact()) { catImageResponse, catFactResponse ->
            catImageResponse to catFactResponse
        }

    private fun Flow<Pair<Response<CatImage>, Response<CatFact>>>.mapToState(): Flow<State<CatFactData>?> =
        map { (catImageResponse, catFactResponse) ->
            if (catImageResponse is Response.Success && catFactResponse is Response.Success) {
                val imageByteArrayResponse = remoteImageHandler.downloadImage(
                    url = catImageResponse.data.url
                )
                if (imageByteArrayResponse is Response.Success) {
                    val data = CatFactData(
                        imageData = imageByteArrayResponse.data,
                        text = catFactResponse.data.text
                    )
                    return@map State(
                        data = data,
                        loadingState = LoadingState.SUCCESS
                    )
                }
            }
            State(
                loadingState = LoadingState.ERROR
            )
        }

    private fun saveLatestDownloadedImageToGallery() {
        _state.value?.data?.let { data ->
            localImageHandler.saveImageToGallery(galleryImageName, data.imageData)
        }
    }

    companion object {
        private const val galleryImageName = "kitten"
    }
}
