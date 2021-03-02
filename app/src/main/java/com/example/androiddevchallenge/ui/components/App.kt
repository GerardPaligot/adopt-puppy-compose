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
package com.example.androiddevchallenge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.androiddevchallenge.ui.PUPPIES
import com.example.androiddevchallenge.ui.Puppy
import com.example.androiddevchallenge.ui.theme.PuppyadoptionTheme
import com.example.androiddevchallenge.ui.theme.green
import com.example.androiddevchallenge.ui.theme.red

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun App(
    puppies: List<Puppy>,
    onSwiped: (result: SwipeableCardResult, puppy: Puppy) -> Unit
) {
    val rejectedState = remember { mutableStateOf(false) }
    val acceptedState = remember { mutableStateOf(false) }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        val (textInput, buttons) = createRefs()
        LocationTextField(
            modifier = Modifier
                .constrainAs(textInput) {
                    top.linkTo(parent.top, 20.dp)
                }
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(buttons) { bottom.linkTo(parent.bottom, 100.dp) }
        ) {
            FloatingActionButton(
                backgroundColor = red,
                contentColor = MaterialTheme.colors.onPrimary,
                onClick = { rejectedState.value = true }
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Reject")
            }
            FloatingActionButton(
                backgroundColor = green,
                contentColor = MaterialTheme.colors.onPrimary,
                onClick = { acceptedState.value = true }
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Adopt")
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            puppies.forEachIndexed { index, puppy ->
                SwipeableCard(
                    autoRejectSwipe = puppies.size - 1 == index && rejectedState.value,
                    autoAcceptSwipe = puppies.size - 1 == index && acceptedState.value,
                    onSwiped = {
                        rejectedState.value = false
                        acceptedState.value = false
                        onSwiped(it, puppy)
                    },
                    modifier = Modifier.fillMaxSize()
                ) {
                    PuppyTransition(
                        puppy = puppy,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(align = Alignment.Center)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MatchScreenPreview() {
    PuppyadoptionTheme {
        App(puppies = arrayListOf(PUPPIES[0])) { _, _ -> }
    }
}
