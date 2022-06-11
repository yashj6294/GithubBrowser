package com.example.githubbrowser.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient{
    companion object {
    private var retrofitClient: Retrofit? = null
        fun getRetrofitClient(): Retrofit {
            if (retrofitClient == null) {
                retrofitClient = Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofitClient!!
        }
    }
}