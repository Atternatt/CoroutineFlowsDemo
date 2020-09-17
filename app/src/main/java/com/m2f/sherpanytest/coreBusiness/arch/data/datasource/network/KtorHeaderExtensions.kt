package com.m2f.sherpanytest.coreBusiness.arch.data.datasource.network

import io.ktor.client.request.*

fun HttpRequestBuilder.headers(values: List<Pair<String, String>>) {
    values.forEach { headers.append(it.first, it.second) }
}