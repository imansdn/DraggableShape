package com.example.simpledraggableshape.home

import com.example.simpledraggableshape.MainCoroutineRule
import com.example.simpledraggableshape.data.source.FakeRectangleRepository
import com.example.simpledraggableshape.domain.model.Movement
import com.example.simpledraggableshape.domain.model.Rectangle
import com.example.simpledraggableshape.domain.model.Response
import com.example.simpledraggableshape.domain.usecase.GetRectanglesUseCase
import com.example.simpledraggableshape.domain.usecase.UpdateRectanglesUseCase
import com.example.simpledraggableshape.ui.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.math.pow
import kotlin.math.roundToInt

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel
    private lateinit var repository: FakeRectangleRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupViewModel() = runTest {
        val testDispatcher = StandardTestDispatcher()
        repository = FakeRectangleRepository(testDispatcher)
        repository.rectangles.add(Rectangle(0, 0.1f, 0.1f, 0.6f, 0.6f, -1, -1))
        repository.rectangles.add(Rectangle(1, 0.3f, 0.3f, 0.1f, 0.1f, -1, -1))
        viewModel =
            HomeViewModel(GetRectanglesUseCase(repository), UpdateRectanglesUseCase(repository))

        advanceUntilIdle() // wait for repo to return values
    }


    @Test
    fun test_dragging_1() = runTest {
        viewModel.updateRectanglePosition(Movement(0, 0.1f, 0.2f, -1))
        (viewModel.rectangles.value as Response.Success<List<Rectangle>>).data?.let { rectangles ->
            checkFloatEquality(rectangles[0].xCorner, 0.2f)
            checkFloatEquality(rectangles[0].yCorner, 0.3f)
            checkFloatEquality(rectangles[1].xCorner, 0.3f)
            checkFloatEquality(rectangles[1].yCorner, 0.3f)
        }

    }

    @Test
    fun test_dragging_2() = runTest(UnconfinedTestDispatcher()) {
        viewModel.updateRectanglePosition(Movement(1, -0.1f, -0.1f, -1))
        (viewModel.rectangles.value as Response.Success<List<Rectangle>>).data?.let { rectangles ->
            checkFloatEquality(rectangles[0].xCorner, 0.1f)
            checkFloatEquality(rectangles[0].yCorner, 0.1f)
            checkFloatEquality(rectangles[1].xCorner, 0.2f)
            checkFloatEquality(rectangles[1].yCorner, 0.2f)
        }
    }

    @Test
    fun test_dragging_precision() = runTest {
        (0 until 100).forEach { _ ->
            viewModel.updateRectanglePosition(Movement(1, -0.001f, -0.001f, -1))
        }
        (viewModel.rectangles.value as Response.Success<List<Rectangle>>).data?.let { rectangles ->
            checkFloatEquality(rectangles[0].xCorner, 0.1f)
            checkFloatEquality(rectangles[0].yCorner, 0.1f)
            checkFloatEquality(rectangles[1].xCorner, 0.2f)
            checkFloatEquality(rectangles[1].yCorner, 0.2f)
        }


    }

    @Test(timeout = 3000)
    fun check_handling_flow_crashing_of_update_rectangles() = runTest {
        repository.forceCrashing = true
        assert(!viewModel.updateRectanglePosition(Movement(1, -0.1f, -0.1f, -1)))
        // note if no error produced, above line will hang and cause test timeout
    }

    private fun checkFloatEquality(a: Float, b: Float, precision: Int = 2) {
        assert((a * 10.0.pow(precision.toDouble())).roundToInt() == (b * 10.0.pow(precision.toDouble())).roundToInt())
    }
}