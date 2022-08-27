package com.example.simpledraggableshape.ui.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simpledraggableshape.R
import com.example.simpledraggableshape.domain.model.Movement
import com.example.simpledraggableshape.domain.model.Rectangle
import com.example.simpledraggableshape.domain.model.Response
import com.example.simpledraggableshape.ui.theme.MyApplicationTheme

@Preview(showBackground = true)
@Composable
fun Preview() {
    MyApplicationTheme {
        HomeScreen()
    }
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Box(Modifier.fillMaxSize()) {
        when (viewModel.rectangles.value) {
            Response.Loading -> {}
            is Response.Success -> {
                (viewModel.rectangles.value as Response.Success<List<Rectangle>>).data?.forEach { rectangle ->
                    DrawRectangle(rect = rectangle, onDrag = { changeX, changeY ->
                        viewModel.moveRectangle(
                            Movement(
                                id = rectangle.id,
                                x = changeX,
                                y = changeY,
                                lastVisit = System.currentTimeMillis()
                            )
                        )
                    })
                }
            }
            is Response.Failure -> {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.fetch_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
            Response.None -> Unit
        }

    }
}

@Composable
fun DrawRectangle(
    rect: Rectangle,
    onDrag: (changeX: Float, changeY: Float) -> Unit
) {
    val screenCfg = LocalConfiguration.current

    val topLeftX = screenCfg.screenWidthDp.dp.times(rect.xCorner)
    val topLeftY = screenCfg.screenHeightDp.dp.times(rect.yCorner)
    val width = screenCfg.screenWidthDp.dp.times(rect.width)
    val height = screenCfg.screenHeightDp.dp.times(rect.height)

    Box(
        modifier = Modifier
            .offset(topLeftX, topLeftY)
            .size(width, height)
            .background(Color(rect.color))
            .border(width = 2.dp, color = MaterialTheme.colors.primaryVariant)
            .testTag("rectangle_${rect.id}")
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val xChange = dragAmount.x.toDp() / screenCfg.screenWidthDp.dp
                    val yChange = dragAmount.y.toDp() / screenCfg.screenHeightDp.dp
                    onDrag(xChange, yChange)
                }
            }
    ) {
        Text(
            text = rect.id.toString(),
            modifier = Modifier

                .align(Alignment.Center),
            textAlign = TextAlign.Center,

            )
    }
}

