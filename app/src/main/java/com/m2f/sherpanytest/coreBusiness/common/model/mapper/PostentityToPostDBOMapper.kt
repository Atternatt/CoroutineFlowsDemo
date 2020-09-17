package com.m2f.sherpanytest.coreBusiness.common.model.mapper

import com.m2f.sherpanytest.coreBusiness.arch.data.mapper.Mapper
import com.m2f.sherpanytest.coreBusiness.common.model.data.entity.PostEntity
import com.m2f.sherpanytest.sqldelight.data.PostDBO


class PostentityToPostDBOMapper: Mapper<PostEntity, PostDBO> {
    override fun map(from: PostEntity): PostDBO = with(from) {
        PostDBO(body, id.toLong(), title, userId.toLong())
    }
}