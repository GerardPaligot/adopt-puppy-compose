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

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.PUPPIES
import com.example.androiddevchallenge.ui.Puppy
import com.example.androiddevchallenge.ui.theme.PuppyadoptionTheme
import com.example.androiddevchallenge.ui.theme.blue
import com.example.androiddevchallenge.ui.theme.green
import com.example.androiddevchallenge.ui.theme.orange
import com.example.androiddevchallenge.ui.theme.red

@Composable
fun Tag(
    @DrawableRes icon: Int,
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(5.dp),
    typography: TextStyle = MaterialTheme.typography.body2
) {
    Column(
        modifier = modifier
            .clip(shape)
            .background(color = color.copy(alpha = .4f), shape = shape)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = text, style = typography, color = color)
    }
}

@Composable
fun PuppyScreen(
    puppy: Puppy,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.padding(40.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 10.dp
    ) {
        Column {
            PuppyCard(
                name = puppy.name,
                shelter = puppy.shelter,
                painter = painterResource(id = puppy.drawableId),
                modifier = Modifier
                    .height(270.dp)
                    .fillMaxWidth(),
                showPuppyName = false,
                shape = RectangleShape
            )
            Spacer(modifier = Modifier.height(20.dp))
            PuppayName(
                name = puppy.name,
                shelter = puppy.shelter,
                modifier = Modifier.padding(start = 20.dp),
                color = Color.Black,
                shadow = null
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Tag(
                    icon = puppy.sexId,
                    text = if (puppy.sexId == R.drawable.female) "Female" else "Male",
                    color = orange
                )
                Tag(icon = R.drawable.candle, text = "${puppy.year} years", color = red)
                Tag(icon = R.drawable.meter, text = puppy.size, color = blue)
                Tag(icon = R.drawable.paw, text = puppy.race, color = green)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "More about ${puppy.name}",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(start = 20.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(text = "ADOPT ME", modifier = Modifier.padding(10.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PuppyScreenPreview() {
    val puppy = PUPPIES[0]
    PuppyadoptionTheme {
        PuppyScreen(puppy = puppy)
    }
}
