/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.modifiers

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
@ExperimentalMaterialApi
fun rememberSwipeableState(maxWidth: Float, maxHeight: Float): SwipeableState =
    remember { SwipeableState(maxWidth, maxHeight) }

open class SwipeableState(val maxWidth: Float, val maxHeight: Float) {
    val offsetX = Animatable(0f)
    val offsetY = Animatable(0f)

    fun reset(scope: CoroutineScope) = scope.launch {
        launch { offsetX.animateTo(0f, tween(400)) }
        launch { offsetY.animateTo(0f, tween(400)) }
    }

    fun accepted(scope: CoroutineScope) = scope.launch {
        offsetX.animateTo(maxWidth * 2, tween(400))
    }

    fun rejected(scope: CoroutineScope) = scope.launch {
        offsetX.animateTo(-(maxWidth * 2), tween(400))
    }

    fun drag(scope: CoroutineScope, x: Float, y: Float) = scope.launch {
        launch { offsetX.animateTo(x) }
        launch { offsetY.animateTo(y) }
    }
}

fun Modifier.swipeable(
    state: SwipeableState,
    autoRejectSwipe: Boolean = false,
    autoAcceptSwipe: Boolean = false,
    onDragReset: () -> Unit = {},
    onDragAccepted: () -> Unit,
    onDragRejected: () -> Unit
): Modifier = composed {
    val scope = rememberCoroutineScope()
    if (autoRejectSwipe) state.rejected(scope)
    else if (autoAcceptSwipe) state.accepted(scope)
    Modifier
        .pointerInput(Unit) {
            detectDragGestures(
                onDragEnd = {
                    when {
                        abs(state.offsetX.targetValue) < state.maxWidth / 4 -> {
                            state
                                .reset(scope)
                                .invokeOnCompletion { onDragReset() }
                        }
                        state.offsetX.targetValue > 0 -> {
                            state
                                .accepted(scope)
                                .invokeOnCompletion { onDragAccepted() }
                        }
                        state.offsetX.targetValue < 0 -> {
                            state
                                .rejected(scope)
                                .invokeOnCompletion { onDragRejected() }
                        }
                    }
                },
                onDrag = { change, dragAmount ->
                    val original = Offset(state.offsetX.targetValue, state.offsetY.targetValue)
                    val summed = original + dragAmount
                    val newValue = Offset(
                        x = summed.x.coerceIn(-state.maxWidth, state.maxWidth),
                        y = summed.y.coerceIn(-state.maxHeight, state.maxHeight)
                    )
                    change.consumePositionChange()
                    state.drag(scope, newValue.x, newValue.y)
                }
            )
        }
        .graphicsLayer(
            translationX = state.offsetX.value,
            translationY = state.offsetY.value,
            rotationZ = (state.offsetX.value / 60).coerceIn(-40f, 40f),
            alpha = ((state.maxWidth - abs(state.offsetX.value)) / state.maxWidth).coerceIn(0f, 1f)
        )
}
