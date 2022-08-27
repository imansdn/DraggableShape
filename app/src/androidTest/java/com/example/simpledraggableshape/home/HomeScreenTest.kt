package com.example.simpledraggableshape.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.room.withTransaction
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.swipeUp
import com.example.simpledraggableshape.MainActivity
import com.example.simpledraggableshape.data.source.local.AppDatabase
import com.example.simpledraggableshape.data.source.network.RetrofitService
import com.example.simpledraggableshape.data.source.network.dto.GetRectangleDto
import com.example.simpledraggableshape.data.source.remote.FakeRetrofitService
import com.example.simpledraggableshape.di.DispatcherModule
import com.example.simpledraggableshape.di.qualifier.IoDispatcher
import com.example.simpledraggableshape.ui.home.HomeScreen
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@UninstallModules(DispatcherModule::class)
@ExperimentalCoroutinesApi
@HiltAndroidTest
class HomeScreenTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var retrofitService: RetrofitService

    @Inject
    lateinit var appDatabase: AppDatabase

    private val scheduler = TestCoroutineScheduler()

    @BindValue
    @IoDispatcher
    val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher(scheduler)

    @Before
    fun setupFakeAPI() {
        hiltRule.inject()
        (retrofitService as FakeRetrofitService).response = GetRectangleDto(
            rectangles = listOf(
                GetRectangleDto.RectangleDto(id = 0, x = 0.2, y = 0.2, width = 0.2f, height = 0.2f),
                GetRectangleDto.RectangleDto(id = 1, x = 0.4, y = 0.4, width = 0.2f, height = 0.2f),
            )
        )
    }

    @Test
    fun check_rectangle_dragging() = runTest(dispatcher) {
        // Start the app
        composeTestRule.activity.setContent {
            HomeScreen()
        }
        composeTestRule.onNodeWithTag("rectangle_0", useUnmergedTree = true)
            .performTouchInput {
                swipeDown(endY = 40_000f, durationMillis = 1000)
            }.performTouchInput {
                swipeRight(endX = 40_000f, durationMillis = 1000)
            }

        composeTestRule.onNodeWithTag("rectangle_1", useUnmergedTree = true)
            .performTouchInput {
                swipeUp(endY = -20_000f, durationMillis = 1000)
            }.performTouchInput {
                swipeLeft(endX = -15_000f, durationMillis = 1000)
            }

        //wait for views to update
        composeTestRule.waitForIdle()

        //wait for db to finish its transaction.
        //It seems it is using its own dispatcher and we cant handle it with dispatcher functions
        appDatabase.withTransaction { }

        val rectangles = appDatabase.rectangleDao().getRectangles()

        val rect1 = rectangles?.find { it.id == 0 }!!
        println("sagg=>rect1=>$rect1")
        assert(rect1.x > 0.4f)
        assert(rect1.y > 0.25f)
        assert(rect1.width == 0.2f)
        assert(rect1.height == 0.2f)

        val rect2 = rectangles.find { it.id == 1 }!!
        println("sagg=>rect2=>$rect2")
        assert(rect2.x < 0.18f)
        assert(rect2.y < 0.22f)
        assert(rect2.width == 0.2f)
        assert(rect2.height == 0.2f)

    }


}