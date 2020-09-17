package com.m2f.sherpanytest.coreBusiness.common.model.mapper

import com.m2f.sherpanytest.coreBusiness.arch.data.mapper.Mapper
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.PostEntity
import com.m2f.sherpanytest.sqldelight.data.PostDBO


class PostDBOtoPostEntityMapper: Mapper<PostDBO, PostEntity> {
    override fun map(from: PostDBO): PostEntity = with(from) {
        PostEntity(body, id.toInt(), title, userId.toInt())
    }
}