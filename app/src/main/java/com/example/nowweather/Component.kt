package com.example.nowweather

data class Component(
    val extra_comment: String,
    val id: Int,
    val ingredient: Ingredient,
    val measurements: List<Measurement>,
    val position: Int,
    val raw_text: String
)