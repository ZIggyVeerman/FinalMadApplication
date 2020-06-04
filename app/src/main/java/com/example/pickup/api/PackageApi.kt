package com.example.pickup.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PackageApi {
    companion object {
        private const val baseUrl = "BASEURL"

        fun createApi(): PackageApiService {
            val okHttpCLient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            val packageApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpCLient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return packageApi.create(PackageApiService::class.java)
        }

    }

}