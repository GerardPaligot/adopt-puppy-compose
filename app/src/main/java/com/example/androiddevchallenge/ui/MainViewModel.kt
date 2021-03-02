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
package com.example.androiddevchallenge.ui

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Puppy(
    val name: String,
    val shelter: String,
    @DrawableRes val drawableId: Int,
    @DrawableRes val sexId: Int,
    val year: Int,
    val size: String,
    val race: String
)

val PUPPIES = arrayListOf(
    Puppy(
        "Alyssa",
        "Refuge la ferme des arches",
        R.drawable.alyssa,
        R.drawable.male,
        4,
        "Large",
        "Border Collie"
    ),
    Puppy("Anna", "Refuge la Loue", R.drawable.anna, R.drawable.male, 6, "Medium", "Unknown"),
    Puppy(
        "Isaac",
        "Refuge la Rochette",
        R.drawable.isaac,
        R.drawable.female,
        3,
        "Medium",
        "Herdsman"
    ),
    Puppy(
        "James",
        "Refuge Le Moulin d'en Haut",
        R.drawable.james,
        R.drawable.male,
        5,
        "Large",
        "Brachet"
    ),
    Puppy("Jamie", "Maison SPA", R.drawable.jamie, R.drawable.female, 2, "Small", "Spaniel"),
    Puppy(
        "Jaycee",
        "Refuge la ferme des arches",
        R.drawable.jaycee,
        R.drawable.female,
        4,
        "Medium",
        "Shiba Inu"
    ),
    Puppy(
        "Marliese",
        "Refuge la Rochette",
        R.drawable.marliese,
        R.drawable.male,
        3,
        "Small",
        "Beagle"
    ),
)

class MainViewModel : ViewModel() {
    private val _puppies = MutableStateFlow(PUPPIES)
    private val swiped = arrayListOf<Puppy>()
    val puppies = _puppies.asStateFlow()

    fun addSwiped(puppy: Puppy) {
        swiped.add(puppy)
        _puppies.value = arrayListOf(*(PUPPIES - swiped).toTypedArray())
    }
}
