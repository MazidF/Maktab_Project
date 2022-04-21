package com.example.maktabplus.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maktabplus.data.ImageRepository
import com.example.maktabplus.data.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelHome @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {

    init {
        getImageList()
    }

    private val _imageListFlow: MutableStateFlow<List<Image>> = MutableStateFlow(listOf())
    val imageListFlow get() = _imageListFlow.asStateFlow()


    fun getImageList() {
        viewModelScope.launch {
            val flow = repository.getImageList()
            flow.collect {
                _imageListFlow.emit(it)
            }
        }
    }
}