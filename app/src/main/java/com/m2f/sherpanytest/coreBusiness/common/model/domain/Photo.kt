package com.m2f.sherpanytest.coreBusiness.common.model.domain

data class Photo(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)