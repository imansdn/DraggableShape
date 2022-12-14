package com.example.simpledraggableshape.ui.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpledraggableshape.domain.model.Movement
import com.example.simpledraggableshape.domain.model.Rectangle
import com.example.simpledraggableshape.domain.model.Response
import com.example.simpledraggableshape.domain.usecase.GetRectanglesUseCase
import com.example.simpledraggableshape.domain.usecase.UpdateRectanglesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRectanglesUseCase: GetRectanglesUseCase,
    private val updateRectanglesUseCase: UpdateRectanglesUseCase,
) : ViewModel() {

    private val _rectangles =
        mutableStateOf<Response<List<Rectangle>>>(Response.Success(emptyList()))
    val rectangles: State<Response<List<Rectangle>>> get() = _rectangles


    init {
        getRectangles()
    }

    private fun getRectangles() {
        getRectanglesUseCase().onEach {
            _rectangles.value = Response.Success(it)
        }.catch {
            _rectangles.value = Response.Failure(it)
            handleError(it)
        }.launchIn(viewModelScope)
    }

    private fun handleError(throwable: Throwable) {
        Log.e(this.javaClass.name, "handleError: $throwable")
    }

    suspend fun updateRectanglePosition(movement: Movement): Boolean {
        val rectangles = (_rectangles.value as? Response.Success)?.data
        return if (rectangles != null) {
            var result = false
            updateRectanglesUseCase(rectangles, movement).catch {
                result = false
            }.collect {
                _rectangles.value = Response.Success(it)
                result = true
            }
            result
        } else {
            false
        }
    }


    fun moveRectangle(movement: Movement) {
        viewModelScope.launch {
            updateRectanglePosition(movement)
        }
    }
}