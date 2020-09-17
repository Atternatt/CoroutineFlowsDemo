package com.m2f.sherpanytest.coreBusiness.domain.features.posts.data.queries

import com.m2f.sherpanytest.coreBusiness.arch.data.query.IdQuery


data class PostQuery(val id: Long) : IdQuery<Long>(id)