package com.example.penndinning

import com.squareup.moshi.Json

data class Dining(
    @Json(name = "id")
    val id: Int,
    @Json(name = "image")
    val image: String = "",
    @Json(name = "address")
    val address: String = "",
    @Json(name = "name")
    val name: String = "",
    @Json(name = "days")
    val days: List<DaysItem>?
)