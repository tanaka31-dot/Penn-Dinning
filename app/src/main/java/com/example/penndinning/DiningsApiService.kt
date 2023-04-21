package com.example.penndinning

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://pennmobile.org/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface DiningsApiService {
    @GET("dining/venues/")
    fun getDinings():
            Call<MutableList<Dining>>
}

object DiningApi {
    val retrofitService: DiningsApiService by lazy {
        retrofit.create(DiningsApiService::class.java)
    }
}