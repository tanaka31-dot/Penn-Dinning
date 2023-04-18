package com.example.penndinning

import com.squareup.moshi.Json

data class DaysItem(
    @Json(name = "date")
    val date: String = "",
    @Json(name = "dayparts")
    val dayparts: List<DaypartsItem>?,
    @Json(name = "status")
    val status: String = ""
)