package dev.carrion.data.network

import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import java.util.concurrent.TimeUnit

/**
 * Interface for Retrofit calls
 */
interface BusinessManApi {

    /**
     *  Get rate list in json format
     */
    @Headers("Accept: application/json")
    @GET("/rates")
    fun getRateList(): Call<List<ApiEntity.Rate>>

    /**
     * Get transaction list in json format
     */
    @Headers("Accept: application/json")
    @GET("/transactions")
    fun getTransactions(): Call<List<ApiEntity.Transaction>>

    companion object {
        private const val BASE_URL  = "http://quiet-stone-2094.herokuapp.com"

        fun create(): BusinessManApi {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .connectTimeout(90L, TimeUnit.SECONDS)
                .readTimeout(90L, TimeUnit.SECONDS)
                .writeTimeout(90L, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper().registerModule(KotlinModule())))
                .build()
                .create(BusinessManApi::class.java)
        }
    }
}