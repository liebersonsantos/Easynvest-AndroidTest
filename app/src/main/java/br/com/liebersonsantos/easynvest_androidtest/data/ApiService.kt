package br.com.liebersonsantos.easynvest_androidtest.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by lieberson on 6/29/21.
 * @author lieberson.xsantos@gmail.com
 */
object ApiService {
    const val URL = "https://run.mocky.io/v3/"

    private fun initRetrofit(baseUrl: String): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging).build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    fun service(url: String): Api = initRetrofit(url).create(Api::class.java)
}