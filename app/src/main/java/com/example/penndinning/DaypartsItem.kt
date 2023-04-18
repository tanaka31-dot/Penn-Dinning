package com.example.penndinning

import com.squareup.moshi.Json

data class DaypartsItem(
    @Json(name = "endtime")
    val endtime: String = "",
    @Json(name = "starttime")
    val starttime: String = "",
    @Json(name = "label")
    val label: String = "",
    @Json(name = "message")
    val message: String = ""
)