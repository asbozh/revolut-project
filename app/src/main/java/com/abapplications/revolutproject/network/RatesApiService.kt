package com.abapplications.revolutproject.network

import com.abapplications.revolutproject.model.CurrencyRates
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://hiring.revolut.codes/api/android/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface RatesApiService {
    @GET("latest")
    suspend fun getProperties(@Query("base") type: String):
            CurrencyRates
}

object RatesApi {
    val retrofitService : RatesApiService by lazy { retrofit.create(RatesApiService::class.java) }
}