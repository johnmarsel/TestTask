package com.johnmarsel.testtask.api

import com.johnmarsel.testtask.model.ItunesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {
    @GET("search?" +
                "&entity=album")
    suspend fun searchAlbums(@Query("term") term: String): Response<ItunesResponse>

    @GET("lookup?" +
            "&entity=song" +
            "&limit=200")
    suspend fun fetchSongs(@Query("id") collectionId: Int): Response<ItunesResponse>


    companion object {

        private var INSTANCE: ItunesApi? = null

        private const val BASE_URL = "https://itunes.apple.com/"

        fun create() {
            if (INSTANCE == null) {
                INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(OkHttpClient.Builder().also { client ->
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ItunesApi::class.java)
            }
        }

        fun get(): ItunesApi {
            return INSTANCE ?:
            throw IllegalStateException("ItunesApi must be initialized")
        }
    }
}