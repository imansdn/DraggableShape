package com.example.simpledraggableshape.data.source

import android.icu.util.Calendar
import com.example.simpledraggableshape.data.repository.RectanglesRepository
import com.example.simpledraggableshape.data.source.local.AppDatabase
import com.example.simpledraggableshape.data.source.network.RetrofitService
import com.example.simpledraggableshape.data.source.network.dto.GetRectangleDto
import com.example.simpledraggableshape.data.source.remote.FakeRetrofitService
import com.example.simpledraggableshape.di.DispatcherModule
import com.example.simpledraggableshape.di.qualifier.IoDispatcher
import com.example.simpledraggableshape.domain.setting.AppSetting
import com.example.simpledraggableshape.util.DateUtil
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@UninstallModules(DispatcherModule::class)
@ExperimentalCoroutinesApi
@HiltAndroidTest
class RectangleRepositoryTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var retrofitService: RetrofitService

    @Inject
    lateinit var appDatabase: AppDatabase

    @BindValue
    @IoDispatcher
    val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    @Inject
    lateinit var rectangleRepository: RectanglesRepository

    @Inject
    lateinit var appSetting: AppSetting

    private val beforeInitCalendar = Calendar.getInstance()

    @Before
    fun setupViewModel() {
        hiltRule.inject()
        appSetting.saveLastVisit(DateUtil.getDateString(beforeInitCalendar.time))
        (retrofitService as FakeRetrofitService).response = GetRectangleDto(
            rectangles = listOf(
                GetRectangleDto.RectangleDto(
                    id = 0,
                    x = 0.25,
                    y = 0.35,
                    width = 0.3f,
                    height = 0.3f
                ),
                GetRectangleDto.RectangleDto(
                    id = 1,
                    x = 0.55,
                    y = 0.75,
                    width = 0.1f,
                    height = 0.1f
                ),
            )
        )
    }

    @Test
    fun check_if_initial_rectangle_got_updated_in_db() = runTest(dispatcher) {
        rectangleRepository.getRectangles().collect()
        val rectangles = appDatabase.rectangleDao().getRectangles()

        val rect1 = rectangles?.find { it.id == 0 }!!
        assert(rect1.x.toFloat() == 0.25f)
        assert(rect1.y.toFloat() == 0.35f)
        assert(rect1.width == 0.3f)
        assert(rect1.height == 0.3f)

        val rect2 = rectangles.find { it.id == 1 }!!
        assert(rect2.x.toFloat() == 0.55f)
        assert(rect2.y.toFloat() == 0.75f)
        assert(rect2.width == 0.1f)
        assert(rect2.height == 0.1f)
    }

    @Test
    fun check_if_initial_last_update_got_updated_in_db() = runTest(dispatcher) {
        rectangleRepository.getRectangles().collect()

        val afterUpdateCalendar = Calendar.getInstance()

        val lastUpdateDate = DateUtil.getDate(appSetting.getLastVisit())

        assert(beforeInitCalendar.time.before(lastUpdateDate))
        assert(afterUpdateCalendar.time.after(lastUpdateDate))
    }

    @Test
    fun check_if_data_got_updated_in_db_after_dragging() = runTest(dispatcher) {
        val rect1 = rectangleRepository.getRectangles().first()[0]

        rectangleRepository.updatePosition(
            rect1.copy(
                xCorner = 0.4f,
                yCorner = 0.9f
            )
        ).collect()

        val rectangles = rectangleRepository.getRectangles().first()
        assert(rectangles[0].xCorner == 0.4f)
        assert(rectangles[0].yCorner == 0.9f)
        assert(rectangles[0].width == 0.3f)
        assert(rectangles[0].height == 0.3f)

        assert(rectangles[1].xCorner == 0.55f)
        assert(rectangles[1].yCorner == 0.75f)
        assert(rectangles[1].width == 0.1f)
        assert(rectangles[1].height == 0.1f)
    }

    @Test
    fun check_if_requesting_API_occur_after_one_week() = runTest(dispatcher) {
        val rectangles1 = rectangleRepository.getRectangles().first()
        assert(rectangles1[0].xCorner == 0.25f)

        val oneWeekAgo = Calendar.getInstance().apply {
            add(Calendar.DATE, -8)
        }
        appSetting.saveLastVisit(DateUtil.getDateString(oneWeekAgo.time))
        assert(DateUtil.isMoreThanWeek(appSetting.getLastVisit()))
        (retrofitService as FakeRetrofitService).response = GetRectangleDto(
            rectangles = listOf(
                GetRectangleDto.RectangleDto(
                    id = 0,
                    x = 0.45,
                    y = 0.45,
                    width = 0.1f,
                    height = 0.1f
                ),
            )
        )
        val rectangles2 = rectangleRepository.getRectangles().first()
        assert(rectangles2[0].xCorner == 0.45f)
    }

    @Test
    fun test_fetching_rectangles() = runTest(dispatcher) {
        val rectangles = rectangleRepository.getRectangles().first()
        assert(rectangles[0].xCorner == 0.25f)
        assert(rectangles[0].yCorner == 0.35f)
        assert(rectangles[0].width == 0.3f)
        assert(rectangles[0].height == 0.3f)
    }
}