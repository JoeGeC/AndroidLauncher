package com.joebarker.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherRemoteCalls {

    @GET("{city}")
    fun retrieveWeatherInfo(
        @Path("city") city: String,
    ): Call<WeatherInfoResponse>

}