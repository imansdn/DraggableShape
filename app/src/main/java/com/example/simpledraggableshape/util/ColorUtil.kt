package com.example.simpledraggableshape.util

import android.graphics.Color
import kotlin.random.Random

fun randomColor(): Int {
    return Color.argb(
        255,
        Random.nextInt(256),
        Random.nextInt(256),
        Random.nextInt(256)
    )
}