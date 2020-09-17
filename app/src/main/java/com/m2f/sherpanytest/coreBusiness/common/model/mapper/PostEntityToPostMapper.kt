package com.m2f.sherpanytest.coreBusiness.common.model.mapper

import com.m2f.sherpanytest.coreBusiness.arch.data.mapper.Mapper
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.PostEntity
import com.m2f.sherpanytest.coreBusiness.common.model.domain.Post


class PostEntityToPostMapper: Mapper<PostEntity, Post> {
    override fun map(from: PostEntity): Post = with(from) {
        Post(body, id, title, userId)
    }
}