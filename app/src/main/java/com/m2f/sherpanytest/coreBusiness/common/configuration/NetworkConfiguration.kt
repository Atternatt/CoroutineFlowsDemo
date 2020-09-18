package com.m2f.sherpanytest.coreBusiness.common.configuration

import io.ktor.client.*
import kotlinx.serialization.json.Json

data class NetworkConfiguration(
    val httpClient: HttpClient,
    val apiPath: String,
    val json: Json,
    val globalHeaders: List<Pair<String, String>> = emptyList()
) {
    val posts: String = "$apiPath/posts"

}