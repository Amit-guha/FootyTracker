package com.example.thefootballshow.di.module

import com.example.thefootballshow.data.api.ApiKeyInterceptor
import com.example.thefootballshow.data.api.NetworkService
import com.example.thefootballshow.di.ApiKey
import com.example.thefootballshow.di.Baseurl
import com.example.thefootballshow.utils.AppConstant
import com.example.thefootballshow.utils.DefaultDispatcherProvider
import com.example.thefootballshow.utils.DispatcherProvider
import com.example.thefootballshow.utils.Logger.AppLogger
import com.example.thefootballshow.utils.Logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /*In Retrofit, GsonConverterFactory is used to convert JSON responses from the server
    into Java/Kotlin objects (serialization)
    and convert Java/Kotlin objects into JSON format for requests (deserialization). Gson*/

    @Provides
    @Singleton
    fun providesNetworkService(
        @Baseurl baseurl: String,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): NetworkService = Retrofit.Builder()
        .baseUrl(baseurl)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
        .create(NetworkService::class.java)

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()


    @Provides
    @Baseurl
    fun provideBaseUrl() = AppConstant.BASE_URL

    @Provides
    @ApiKey
    fun providesApikey() = AppConstant.API_KEY

    @Provides
    @Singleton
    fun providesApiKeyInterceptor(@ApiKey apiKey: String): ApiKeyInterceptor =
        ApiKeyInterceptor(apiKey)

    @Provides
    @Singleton
    fun providesOkHttpClient(
        apiKeyInterceptor: ApiKeyInterceptor,
    ): OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES)
        .retryOnConnectionFailure(true)
        .build()


    @Provides
    @Singleton
    fun providesDispatchers(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    @Singleton
    fun providesLogger(): Logger = AppLogger()

    @Provides
    @Singleton
    fun provideCurrentYear(): Int {
        return Calendar.getInstance().get(Calendar.YEAR)
    }

    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }


}