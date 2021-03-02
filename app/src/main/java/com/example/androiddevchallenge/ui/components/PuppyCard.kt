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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.theme.PuppyadoptionTheme

@Composable
fun PuppayName(
    name: String,
    shelter: String,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    shadow: Shadow? = Shadow(color = Color.Black, blurRadius = 1f, offset = Offset(0f, 2f)),
    nameTextStyle: TextStyle = MaterialTheme.typography.h5.copy(shadow = shadow, color = color),
    shelterTextStyle: TextStyle = MaterialTheme.typography.body1.copy(
        shadow = shadow,
        color = color
    )
) {
    Column(modifier = modifier) {
        Text(
            text = name,
            style = nameTextStyle,
            fontWeight = FontWeight.W700,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.TwoTone.Place,
                tint = shelterTextStyle.color,
                contentDescription = null,
                modifier = Modifier.height(shelterTextStyle.fontSize.value.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = shelter,
                style = shelterTextStyle,
                fontWeight = FontWeight.W400,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun PuppyCard(
    name: String,
    shelter: String,
    painter: Painter,
    modifier: Modifier = Modifier,
    showPuppyName: Boolean = true,
    shape: Shape = RoundedCornerShape(5.dp)
) {
    Surface(modifier = modifier, shape = shape) {
        BoxWithConstraints {
            Image(
                painter = painter,
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            if (showPuppyName) {
                PuppayName(
                    name = name,
                    shelter = shelter,
                    modifier = Modifier
                        .align(alignment = Alignment.BottomStart)
                        .padding(10.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PuppyCardPreview() {
    PuppyadoptionTheme {
        PuppyCard(
            name = "Lady",
            shelter = "Refuge la ferme des arches",
            painter = painterResource(id = R.drawable.isaac),
            modifier = Modifier.size(250.dp)
        )
    }
}
