package com.m2f.sherpanytest.coreBusiness.di

import com.m2f.sherpanytest.coreBusiness.common.configuration.NetworkConfiguration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {


    @Provides
    @Singleton
    fun networkConfiguration(httpClient: HttpClient, json: Json): NetworkConfiguration {
        return NetworkConfiguration(
            httpClient,
            "https://jsonplaceholder.typicode.com",
            json,
            globalHeaders = emptyList()
        )
    }

    @Provides
    @Singleton
    fun json(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    fun httpClient(): HttpClient {
        return HttpClient {
            install(JsonFeature) {
                serializer = defaultSerializer()
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }
}